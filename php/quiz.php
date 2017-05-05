<?php
$conn = mysqli_connect("localhost:3306","root","root");
mysqli_select_db($conn,"quiz");

$id =$_POST['id'];

$result = mysqli_query($conn,"SELECT * FROM maintable where id=".$id);




while($row = mysqli_fetch_array($result)) {
	$question=$row['ques'];
	$option1=$row['a1'];
	$option2=$row['a2'];
	$option3=$row['a3'];
	$option4=$row['a4'];
}

$arr_res=array('id'=>$id,'question'=>$question,'option1'=>$option1,'option2'=>$option2,'option3'=>$option3,'option4'=>$option4);
echo json_encode($arr_res);
mysqli_close($conn);
?>