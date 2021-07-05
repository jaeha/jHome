// scan.js

var width = 0;

$(document).ready(function () {
    console.log("page is ready!!")
    $.getJSON(window.location.href.concat('/progress'), function(percentage) {
        console.log("percentage: " + percentage);
        if (percentage != 0) {
        console.log("scan is executing");
        toggleButton("start");
        getProgress();
        refreshProgress();
      } else {
        toggleButton("stop");
      }
    });
});

function getProgress() {
console.log("getProgress()");
$.getJSON(window.location.href.concat('/progress'), function(percentage) {
  $('#progressBar').css('width', percentage+'%');
  document.getElementById("label").innerHTML = percentage * 1 + '%';
  width = percentage;
  console.log("percentage " + percentage);
});
}

function start(dirs) {
  /*  $.ajax({
      type: "post",
      data: $('#task').serialize(),
      success: function(data) {
      $('#progressBar').css('width', 100+'%');
        document.getElementById("label").innerHTML = 100 * 1 + '%';
        console.log(data);

      }
    });
*/

console.log("scan.js: directoies: " + dirs);

    width = 1;
    toggleButton("start");
    $('#progressBar').css('width', 0+'%');
    document.getElementById("label").innerHTML = 0 * 1 + '%';
    refreshProgress();

 //   $.getJSON(window.location.href.concat('/start'), function(data) {
 //   });

    $.ajax({
       url:'scan/start',
       type: 'POST',
       data: JSON.stringify(dirs),
       contentType: 'application/json; charset=utf-8',
       dataType: 'JSON',
       success: function(data) {
           $('#progressBar').css('width', 0+'%');
           document.getElementById("label").innerHTML = 100 * 1 + '%';
        //   console.log(data);
       }
     });
}

function stop() {
    $.getJSON(window.location.href.concat('/stop'), function(data) {
    });
    width = 0;
    toggleButton("stop");
}

function refreshProgress() {
    console.log("refreshProgress(), width=" + width)
    var id = setInterval(frame, 1000);
    function frame() {
        if (width >= 100 || width == 0) {
            clearInterval(id);
            toggleButton("stop");
        } else {
            getProgress();
        }
    }
}

function toggleButton(taskStatus) {
    if (taskStatus == "start") {
        $("#btnStart").prop("disabled",true);
        $("#btnStop").prop("disabled",false);
    } else { // stop
        $("#btnStart").prop("disabled",false);
        $("#btnStop").prop("disabled",true);
    }
}
