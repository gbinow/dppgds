<?php

if (!defined('DP_BASE_DIR')) {
	die('You should not access this file directly.');
}


global $cBuffer;

$AppUI->savePlace();
$q = new DBQuery();

// load the companies class to retrieved denied companies
require_once ($AppUI->getModuleClass('companies'));
require_once ($AppUI->getModuleClass('projects'));


// retrieve any state parameters
if (isset($_GET['tab'])) {
    $AppUI->setState('ProjIdxTab', $_GET['tab']);
}

$tab = $AppUI->getState('ProjIdxTab') !== NULL ? $AppUI->getState('ProjIdxTab') : 500;
$currentTabId = $tab;
$active = intval(!$AppUI->getState('ProjIdxTab'));

if (isset($_POST['company_id'])) {
    $AppUI->setState('ProjIdxCompany', intval($_POST['company_id']));
}
$company_id = (($AppUI->getState('ProjIdxCompany') !== NULL)
    ? $AppUI->getState('ProjIdxCompany')
    : $AppUI->user_company);

$company_prefix = 'company_';

if (isset($_POST['department'])) {
    $AppUI->setState('ProjIdxDepartment', $_POST['department']);

    //if department is set, ignore the company_id field
    unset($company_id);
}
$department = (($AppUI->getState('ProjIdxDepartment') !== NULL)
    ? $AppUI->getState('ProjIdxDepartment')
    : ($company_prefix . $AppUI->user_company));

//if $department contains the $company_prefix string that it's requesting a company
// and not a department.  So, clear the $department variable, and populate the $company_id variable.
if (!(mb_strpos($department, $company_prefix)===false)) {
    $company_id = mb_substr($department,mb_strlen($company_prefix));
    $AppUI->setState('ProjIdxCompany', $company_id);
    unset($department);
}

$valid_ordering = array('project_name', 'user_username', 'my_tasks desc', 'total_tasks desc',
    'total_tasks', 'my_tasks', 'project_color_identifier', 'company_name',
    'project_end_date', 'project_start_date', 'project_actual_end_date',
    'task_log_problem DESC,project_priority', 'project_status',
    'project_percent_complete');

$orderdir = $AppUI->getState('ProjIdxOrderDir') ? $AppUI->getState('ProjIdxOrderDir') : 'asc';
if (isset($_GET['orderby']) && in_array($_GET['orderby'], $valid_ordering)) {
    $orderdir = (($AppUI->getState('ProjIdxOrderDir') == 'asc') ? 'desc' : 'asc');
    $AppUI->setState('ProjIdxOrderBy', $_GET['orderby']);
}
$orderby = (($AppUI->getState('ProjIdxOrderBy'))
    ? $AppUI->getState('ProjIdxOrderBy') : 'project_end_date');
$AppUI->setState('ProjIdxOrderDir', $orderdir);

// prepare the users filter
if (isset($_POST['show_owner'])) {
    $AppUI->setState('ProjIdxowner', intval($_POST['show_owner']));
}
$owner = $AppUI->getState('ProjIdxowner') !== NULL ? $AppUI->getState('ProjIdxowner') : 0;

$q->addTable('users', 'u');
$q->addJoin('contacts', 'c', 'c.contact_id = u.user_contact');
$q->addQuery('user_id');
$q->addQuery("CONCAT(contact_last_name, ', ', contact_first_name, ' (', user_username, ')')"
    . ' AS label');
$q->addOrder('contact_last_name, contact_first_name, user_username');
$userRows = array(0 => $AppUI->_('All Users', UI_OUTPUT_RAW)) + $q->loadHashList();
$bufferUser = arraySelect($userRows, 'show_owner',
    'class="text" onchange="document.pickUser.submit()""', $owner);

/* setting this to filter project_list_data function below
 0 = undefined
 3 = active
 5 = completed
 7 = archived

Because these are "magic" numbers, if the values for ProjectStatus change under 'System Admin',
they'll need to change here as well (sadly).
*/
if ($tab != 7 && $tab != 8) {
    $project_status = $tab;
} else if ($tab == 0) {
    $project_status = 0;
}
if ($tab == 5 || $tab == 7) {
    $project_active = 0;
}

//for getting permissions for records related to projects
$obj_project = new CProject();
// collect the full (or filtered) projects list data via function in projects.class.php
projects_list_data();

// setup the title block
$titleBlock = new CTitleBlock('Export project data for PGDS-GPS', null, $m, ($m . '.' . $a));

$titleBlock->show();

$project_types = dPgetSysVal('ProjectStatus');

// count number of projects per project_status
$q->addTable('projects', 'p');
$q->addQuery('p.project_status, COUNT(p.project_id) as count');
$obj_project->setAllowedSQL($AppUI->user_id, $q, null, 'p');
if ($owner > 0) {
    $q->addWhere('p.project_owner = ' . $owner);
}
if (isset($department)) {
    $q->addJoin('project_departments', 'pd', 'pd.project_id = p.project_id');
    if (!$addPwOiD) {
        $q->addWhere('pd.department_id in (' . implode(',',$dept_ids) . ')');
    }
} else if ($company_id &&!$addPwOiD) {
    $q->addWhere('p.project_company = ' . $company_id);
}
$q->addGroup('project_status');
$statuses = $q->loadHashList('project_status');
$q->clear();
$all_projects = 0;
foreach ($statuses as $k => $v) {
    $project_status_tabs[$v['project_status']] = ($AppUI->_($project_types[$v['project_status']])
        . ' (' . $v['count'] . ')');
    //count all projects
    $all_projects += $v['count'];
}

// tabbed information boxes
$tabBox = new CTabBox('?m=projects', DP_BASE_DIR . '/modules/pgds/', $tab);

$tabBox->add('vw_idx_proposed', $AppUI->_('All') . ' (' . $all_projects . ')' , true,  500);

//$min_view = true;
$tabBox->show();