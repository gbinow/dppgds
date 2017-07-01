<?php
if (!defined('DP_BASE_DIR')) {
	die('You should not access this file directly.');
}

/**
 *  Name: Hello World
 *  Directory: hello
 *  Version 1.0
 *  Type: user
 *  UI Name: Hello World
 *  UI Icon: ?
 */

$config = array();
$config['mod_name'] = 'PGDS'; // name the module
$config['mod_version'] = '1.0'; // add a version number
$config['mod_directory'] = 'pgds'; // tell dotProject where to find this module
$config['mod_setup_class'] = 'CSetupPgds'; // the name of the PHP setup class (used below)
$config['mod_type'] = 'user'; //'core' for standard dP modules, 'user' for additional modules from dotmods
$config['mod_ui_name'] = 'PGDS-GPS'; // the name that is shown in the main menu of the User Interface
$config['mod_ui_icon'] = 'folder5.png'; // name of a related icon
$config['mod_description'] = 'Módulo de integração com a ferramenta PGDS-GPS'; // some description of the module
$config['mod_config'] = false; // show 'configure' link in viewmods
// $config['permissions_item_table'] = 'hello'; // tell dotProject the database table name
// $config['permissions_item_field'] = 'hello_id'; // identify table's primary key (for permissions)
// $config['permissions_item_label'] = 'hello_text'; // identify "title" field in table


if (@$a == 'setup') {
	echo dPshowModuleConfig($config);
}

// TODO: To be completed later as needed.
class CSetupPgds {

	function configure() { return true; }

	function remove() {
		$q = new DBQuery();
		$q->dropTable('hello');
		$q->exec();
	}

	function upgrade($old_version) {
		// Place to put upgrade logic, based on the previously installed version.
		// Usually handled via a switch statement.
		// Since this is the first version of this module, we have nothing to update.
		return true;
	}

	function install() {
		// $q = new DBQuery();
		// $q->createTable('hello');
		// $q->createDefinition("(
// `hello_id` int(11) NOT NULL AUTO_INCREMENT ,
// `hello_text` varchar(255) NOT NULL default '',
// PRIMARY KEY (`hello_id`)
// ) TYPE = MYISAM ");

		// $q->exec($sql);

		return NULL;
	}
}