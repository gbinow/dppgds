<?php

require_once __DIR__ . '/base.php';

$pdgsData = array_remove_empty($request);

$jsonPath = '../output/'.$pdgsData['project'].'.json';
file_put_contents($jsonPath , json_encode($pdgsData));

shell_exec("/usr/bin/java -jar odtgen.jar $jsonPath");

function array_remove_empty($haystack)
{
    foreach ($haystack as $key => $value) {
        if (is_array($value)) {
            $haystack[$key] = array_remove_empty($haystack[$key]);
        }

        if (empty($haystack[$key])) {
            unset($haystack[$key]);
        }
    }

    return $haystack;
}