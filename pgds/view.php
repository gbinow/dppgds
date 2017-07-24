<?php /* PROJECTS $Id$ */


if (!defined('DP_BASE_DIR')) {
	die('You should not access this file directly.');
}

global $project_id;
global $project_name;



require_once ($AppUI->getModuleClass('projects'));
$project_id = intval(dPgetParam($_GET, 'project_id', 0));


//check permissions for this record
$canAccess = getPermission($m, 'access', $project_id);
$canRead = getPermission($m, 'view', $project_id);
$canEdit = getPermission($m, 'edit', $project_id);

$canAuthorTask = getPermission('tasks', 'add');

//Check if the proect is viewable.
if (!($canRead)) {
$AppUI->redirect('m=public&a=access_denied');
}

$q = new DBQuery;


$q->addTable('projects');
$q->addQuery('project_name');
$q->addWhere('project_id = '.$project_id );
$projects = $q->loadList();
$project_name = $projects[0]['project_name'];

$q->clear();
$q->addTable('tasks');
$q->addQuery('*');
$q->addWhere('task_project = '.$project_id . ' AND task_parent = task_id');

$tasks = $q->loadList();

$df = $AppUI->getPref('SHDATEFORMAT');//.' ' . $AppUI->getPref('TIMEFORMAT');
$durnTypes = dPgetSysVal('TaskDurationType');


?>
<script src="modules/pgds/js/angular.min.js"></script>
<script src="modules/pgds/js/pgds.js"></script>



<div ng-app="pgds" ng-controller="PgdsCtrl as vm" ng-cloak>

	<div style="text-align: center; margin-top: 20px">
		<button ng-click="vm.exportCosts()" ng-disabled="vm.exporting">
			<span ng-if="!vm.exporting">Export data for PGDS</span>
			<span ng-if="vm.exporting">Exporting...</span>
		</button>
	</div>

	<form name="editFrm" action="" method="post">
		<h3>Schedule</h3>
		<table border="1" cellpadding="4" cellspacing="0" width="100%" class="std">

			<tr>
				<td><?php echo $AppUI->_('Task Id'); ?></td>
				<td><?php echo $AppUI->_('Task Name'); ?></td>
				<td><?php echo $AppUI->_('Subtask Name'); ?></td>
				<td><?php echo $AppUI->_('Depend.'); ?></td>
				<td><?php echo $AppUI->_('Planned Start Date'); ?></td>
				<td><?php echo $AppUI->_('Planned End Date'); ?></td>
				<td><?php echo $AppUI->_('Planned Duration'); ?></td>
				<td><?php echo $AppUI->_('Human Resources'); ?></td>
				<td><?php echo $AppUI->_('Start Date'); ?></td>
				<td><?php echo $AppUI->_('End Date'); ?></td>
				<td><?php echo $AppUI->_('Duration'); ?></td>
				<td><?php echo $AppUI->_('%'); ?></td>
			</tr>
			
			<?php 
			$idsMap = array();
			$currId = 1;
			foreach($tasks as $incr => &$_task){

				$idsMap[$_task['task_id']] = $currId++;

				$q->clear();
				$q->addTable('tasks');
				$q->addQuery('*');
				$q->addWhere('task_parent = '.$_task['task_id'] . ' AND task_parent <> task_id');
				$subtasks = $q->loadList();

				$task = &$_task;
				$isSubtask = false;
				$parentTaskId = $taskId = $idsMap[$task['task_id']];
				$task['id'] = (string)$taskId;
				include 'vw_task_line.php';


				$isSubtask = true;
				foreach($subtasks as $i=>&$_subtask){
					$task = &$_subtask;
					$taskId = $parentTaskId . '.' . ($i+1);
					$idsMap[$task['task_id']] = $taskId;
					$task['id'] = (string)$taskId;
					include 'vw_task_line.php';
				}
				
				$_task['subtasks'] = $subtasks;
			}
			?>
		</table>
	</form>

	<?php

	$q->clear();
	$q->addTable('tasks','t');
	$q->addQuery('distinct(u.user_id) , CONCAT(c.contact_first_name, \' \' ,c.contact_last_name) as contact_full_name , pc.cost');
	$q->addJoin('user_tasks', 'ut' ,'ut.task_id = t.task_id','inner');
	$q->addJoin('users', 'u' ,'ut.user_id = u.user_id','inner');
	$q->addJoin('contacts', 'c' ,'c.contact_id = u.user_contact','inner');
	$q->addJoin('user_project_cost', 'pc' ,"pc.project_id = $project_id and u.user_id = pc.user_id",'left');
	$q->addWhere("t.task_project = $project_id");
	$user_costs = $q->loadList();

	?>
	<form name="hrcosts" method="post" >
		<h3>Human Resources Costs</h3>
		<table border="1" cellpadding="4" cellspacing="0" width="100%" class="std">
			<tr>
				<td><?php echo $AppUI->_('HR Id'); ?></td>
				<td><?php echo $AppUI->_('HR Name'); ?></td>
				<td><?php echo $AppUI->_('HR Project Cost'); ?></td>
			</tr>
			<?php foreach($user_costs as $uc){?>
				<tr>
					<td><?=$uc['user_id']?></td>
					<td><?=$uc['contact_full_name']?></td>
					<td>
						<input type="text" 
						ng-model="vm.costs[<?=$uc['user_id']?>]" 
						ng-change="vm.updateHrCosts()">
					</td>
				</tr>
			<?php }?>
		</table>


	</form>

	<div style="text-align: center; margin-top: 20px">
		
	
		<button ng-click="vm.exportCosts()" ng-disabled="vm.exporting">
			<span ng-if="!vm.exporting">Export data for PGDS</span>
			<span ng-if="vm.exporting">Exporting...</span>
		</button>
	
	</div>


</div>

<script>
	var hr = <?=json_encode($user_costs)?>;
	var tasks = <?=json_encode($tasks)?>;
	var projectId = <?=$project_id?>;
	var projectName = "<?=$project_name?>";
</script>