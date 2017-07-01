<?php
$startDate = null;
$endDate = null;
$duration = 0;

$q->clear();
$q->addTable('task_log');
$q->addQuery('*');
$q->addWhere('task_log_task= '.$task['task_id']);
$logs = $q->loadList();


$q->clear();
$q->addTable('user_tasks' , 'ut');
$q->addQuery('u.user_id, CONCAT(c.contact_first_name, \' \' ,c.contact_last_name) as contact_full_name');
$q->addJoin('users', 'u' ,'ut.user_id = u.user_id','inner');
$q->addJoin('contacts', 'c' ,'c.contact_id = u.user_contact','inner');
$q->addWhere('ut.task_id= '.$task['task_id']);
$hrs = $q->loadList();

//if(count($hrs) > 0){
    $hrsNames= implode(', ' , array_map(function($hr){
        return $hr['contact_full_name'];
    } , $hrs));

//}else{
//    $hrsNames = '';
//}

$task['hrs'] = $hrsNames;


foreach($logs as $log){
    if(!$startDate || ($log['task_log_date'] < $startDate)){
        $startDate = $log['task_log_date'];
    }
    if(!$endDate || ($log['task_log_date'] > $endDate)){
        $endDate = $log['task_log_date'];
    }
    $duration += $log['task_log_hours'];

}
$task['realStartDate'] = $startDate;
$task['realEndDate'] = $endDate;
$task['realDuration'] = $duration;
?>
<tr>
    <td>
        <?=$taskId?>
    </td>
    <td>
        <?php if(!$isSubtask) echo $task['task_name'];?>
    </td>
    <td>
        <?php if($isSubtask) echo $task['task_name'];?>
    </td>
    <td>
        <?php
        if($task['task_start_date']){
            $date = new CDate($task['task_start_date']);
            echo $date->format($df);
        }
        ?>
    </td>
    <td>
        <?php
        if($task['task_end_date']){
            $date = new CDate($task['task_end_date']);
            echo $date->format($df);
        }
        ?>
    </td>
    <td>
        <?= $task['task_duration'].' '.$AppUI->_($durnTypes[$task['task_duration_type']])?>
    </td>
    <td><?=$hrsNames?></td>

    <td>
        <?php
        if($startDate){
            $date = new CDate($startDate);
            echo $date->format($df);
        }
        ?>
    </td>
    <td>
        <?php
        if($endDate){
            $date = new CDate($endDate);
            echo $date->format($df);
        }
        ?>
    </td>
    <td><?=$duration.' '.$AppUI->_($durnTypes[$task['task_duration_type']])?></td>
</tr>