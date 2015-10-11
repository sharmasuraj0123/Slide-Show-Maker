/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//var xmlhttp = new XMLHttpRequest();
var url = "./slideshow.json";
//xmlhttp.open("GET", url, true);
//xmlhttp.send();
var obj;
var x

 var count = 0;
 
 var play = false;

$.getJSON(url, function(data) {
 obj = data;

document.getElementById("image").src = obj.slides[count].image_path;


document.getElementById("boss").innerHTML =
obj.slides[0].caption;


document.getElementById("next").addEventListener("click", next);
document.getElementById("prev").addEventListener("click", prev);
document.getElementById("show").addEventListener("click", show);


function next(){
   count++;
   
   if(count >= obj.slides.length)
       count =0;

     document.getElementById("image").src = obj.slides[count].image_path;;
  
  //show_image(obj.slides[count].image_path, 400, 400);
  document.getElementById("boss").innerHTML =
  obj.slides[count].caption;
}

function prev(){
   count--;
   if(count < 0)
       count =obj.slides.length-1;
  
  
  document.getElementById("image").src = obj.slides[count].image_path;
  
  document.getElementById("boss").innerHTML =
  obj.slides[count].caption;
}

function show(){
    //play = !(play);
    
    var next= "next()";
        setTimeout(next, 3000);
        show();
}


});



