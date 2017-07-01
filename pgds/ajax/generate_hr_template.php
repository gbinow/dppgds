<?php

require_once __DIR__ . '/base.php';

$pdgsData = array_remove_empty($request);


file_put_contents(__DIR__. '/../output/pgds.json' , json_encode($pdgsData));

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