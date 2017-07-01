<?php

require_once __DIR__ . '/base.php';

foreach($request['costs'] as $hrId => $cost){
    $q = new DBQuery();
    $q->addTable('user_project_cost');
    $q->addReplace('cost' , $cost);
    $q->addReplace('project_id' , $request['project'] );
    $q->addReplace('user_id' , $hrId );
    $q->exec();
}



