<?php

$conn = mysqli_connect("localhost:3306","root","root");
mysqli_select_db($conn,"quiz");


$question = $_POST['question'];
$answer = $_POST['answer'];
$id = $_POST['id'];

$check='false';

$result = mysqli_query($conn,'select * from answers where id='.$id);

$row = mysqli_fetch_array($result);

$real_answer=$row['answer'];

if($real_answer==$answer)
{
	$check='true';
}



echo json_encode(array('check'=>$check,'id'=>$id));
?>