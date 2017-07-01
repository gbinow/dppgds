<?php

require_once __DIR__ . '/../../../base.php';
require_once DP_BASE_DIR . '/includes/config.php';
require_once (DP_BASE_DIR . '/includes/main_functions.php');
require_once (DP_BASE_DIR . '/includes/db_adodb.php');
require_once (DP_BASE_DIR . '/includes/db_connect.php');

require_once (DP_BASE_DIR . '/classes/ui.class.php');
require_once (DP_BASE_DIR . '/classes/permissions.class.php');
require_once (DP_BASE_DIR . '/includes/session.php');

$postdata = file_get_contents("php://input");
$request = json_decode($postdata , true);