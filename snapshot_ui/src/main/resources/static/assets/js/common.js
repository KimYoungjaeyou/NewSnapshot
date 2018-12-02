$(function(){
  $(".accordion .btn-block > button[data-toggle='collapse']").on('click',function(e){
    $(this).find('span').toggleClass('plus minus');
		var cont = $(this).parent().next();
		cont.slideToggle("300");
})
})

function toggleDataSeries(e) {
  if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
    e.dataSeries.visible = false;
  } else {
    e.dataSeries.visible = true;
  }
  e.chart.render();
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

//에러발생시
$(function(){
  $('#error_handle2').click(function(e){
    e.preventDefault();
     $('#error_handle').addClass('display-none')
     location.reload();
  })
})

$(function(){
  $('#error_handle3').click(function(e){
    e.preventDefault();
     $('#error_handle').addClass('display-none')
     location.reload();
  })
})

//datepicker
$(document).ready(function () {
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
		'7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '',
		showOn: 'both',
    buttonImage: '../assets/img/bg-calendar.png',
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		yearRange: 'c-99:c+99',
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	var today = new Date();
	var beforeday = new Date();
	var today_month = (today.getMonth() + 1);
	var today_day = today.getDate();
  var today_year = today.getFullYear();
  var lastday = ( new Date( today_year, today_month, 0) ).getDate();
	today_month = (today_month < 10) ? "0"+today_month : today_month;
	today_day = (today_day < 10) ? "0"+today_day : today_day;
	var today_date = today.getFullYear() + "-" + today_month + "-" + today_day;

	beforeday.setDate(today.getDate()-7);
	var before_month = (beforeday.getMonth() + 1);
	var before_day = beforeday.getDate();
	before_month = (before_month < 10) ? "0"+before_month : before_month;
	before_day = (before_day < 10) ? "0"+before_day : before_day;
	var before_date = today.getFullYear() + "-" + today_month + "-" + 01;

	$("#api_startdt").val(before_date);
	$("#api_enddt").val(today_date);

	$('#api_startdt').datepicker();
	$('#api_startdt').datepicker("option", "maxDate", $("#api_enddt").val());
	$('#api_startdt').datepicker("option", "onClose", function ( selectedDate ) {
    $("#api_enddt").datepicker( "option", "minDate", selectedDate );
    var stxt = $("#api_startdt").val().split("-");
    if(stxt[1]==today_month){
    	var setdate = today.getFullYear() + "-" + today_month + "-" + today_day;
        $("#api_enddt").val(setdate)
    }else{
      var fsetdate = (new Date(stxt[0], stxt[1], 0)).getDate();
      var setdate = stxt[0] + "-" + stxt[1] + "-" + fsetdate
          $("#api_enddt").val(setdate)

    }
  });

	$('#api_enddt').datepicker();
	$('#api_enddt').datepicker("option", "minDate", $("#api_startdt").val());
	$('#api_enddt').datepicker("option", "beforeShow", function () {
		var stxt = $("#api_startdt").val().split("-");
    if(stxt[1]==today_month){
        $("#api_enddt").datepicker( "option", "maxDate", today )
    }else{
      var mdate = new Date(stxt[0], stxt[1], 0);
      $("#api_enddt").datepicker( "option", "maxDate", mdate );
    }
	});
	$('#api_enddt').datepicker("option", "onClose", function ( selectedDate ) {
		$("#api_startdt").datepicker( "option", "maxDate", selectedDate );
	});

  $("#ss_startdt").val(before_date);
  $("#ss_enddt").val(today_date);

  $('#ss_startdt').datepicker();
  $('#ss_startdt').datepicker("option", "maxDate", $("#ss_enddt").val());
  $('#ss_startdt').datepicker("option", "onClose", function ( selectedDate ) {
    $("#ss_enddt").datepicker( "option", "minDate", selectedDate );
    var stxt = $("#ss_startdt").val().split("-");
    if(stxt[1]==today_month){
    	var setdate = today.getFullYear() + "-" + today_month + "-" + today_day;
        $("#ss_enddt").val(setdate)
    }else{
      var fsetdate = (new Date(stxt[0], stxt[1], 0)).getDate();
      var setdate = stxt[0] + "-" + stxt[1] + "-" + fsetdate
          $("#ss_enddt").val(setdate)

    }
  });

  $('#ss_enddt').datepicker();
  $('#ss_enddt').datepicker("option", "minDate", $("#ss_startdt").val());
  $('#ss_enddt').datepicker("option", "beforeShow", function () {
    var stxt = $("#ss_startdt").val().split("-");
    if(stxt[1]==today_month){
        $("#ss_enddt").datepicker( "option", "maxDate", today )
    }else{
      var mdate = new Date(stxt[0], stxt[1], 0);
      $("#ss_enddt").datepicker( "option", "maxDate", mdate );
    }
  });
  $('#ss_enddt').datepicker("option", "onClose", function ( selectedDate ) {
    $("#ss_startdt").datepicker( "option", "maxDate", selectedDate );
  });


  $("#icos_startdt").val(before_date);
  $("#icos_enddt").val(today_date);

  $('#icos_startdt').datepicker();
  $('#icos_startdt').datepicker("option", "onClose", function ( selectedDate ) {
  $('#icos_startdt').datepicker("option", "maxDate", $("#icos_enddt").val());
    $("#icos_enddt").datepicker( "option", "minDate", selectedDate );
    var stxt = $("#icos_startdt").val().split("-");
    if(stxt[1]==today_month){
    	var setdate = today.getFullYear() + "-" + today_month + "-" + today_day;
        $("#icos_enddt").val(setdate)
    }else{
      var fsetdate = (new Date(stxt[0], stxt[1], 0)).getDate();
      var setdate = stxt[0] + "-" + stxt[1] + "-" + fsetdate
          $("#icos_enddt").val(setdate)

    }
  });

  $('#icos_enddt').datepicker();
  $('#icos_enddt').datepicker("option", "minDate", $("#icos_startdt").val());
  $('#icos_enddt').datepicker("option", "beforeShow", function () {
    var stxt = $("#icos_startdt").val().split("-");
    if(stxt[1]==today_month){
        $("#icos_enddt").datepicker( "option", "maxDate", today )
    }else{
      var mdate = new Date(stxt[0], stxt[1], 0);
      $("#icos_enddt").datepicker( "option", "maxDate", mdate );
    }
  });
  $('#icos_enddt').datepicker("option", "onClose", function ( selectedDate ) {
    $("#sicos_startdt").datepicker( "option", "maxDate", selectedDate );
  });
});
