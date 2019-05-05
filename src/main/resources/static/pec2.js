var words = [
  {text: "Waiting", weight: 15},  
];

$("#startButton").click(function(){ 
	$.getJSON('http://localhost:8080/start/HASHTAG/NO_TAG_FILTER', function(data) {
    	$('#' + value).jQCloud('update', data);
    });
});

$('#word').jQCloud(words);
$('#hashtag').jQCloud(words);
$('#mention').jQCloud(words);

function process(value){
    $.getJSON('http://localhost:8080/words/' + value, function(data) {
    	$('#' + value).jQCloud('update', data);
    });
    setTimeout(function(){
	    process(value);},5000);
}

process('word');
process('hashtag');
process('mention');