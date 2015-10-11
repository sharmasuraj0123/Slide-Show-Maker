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

$.getJSON(url, function(data) {
 x = data;

document.getElementById("boss").innerHTML =
x.slides[0].caption + " " + x.slides[0].caption;
});






function show_image(src, width, height) {
    var img = document.createElement("img");
    img.src = src;
    img.width = width;
    img.height = height;
    
   

    // This next line will just add it to the <body> tag
    document.body.appendChild(img);
}