//icos
$(function(){
 $("#icos_data_button").click(function(e) {
    var param = "prdstartDate="+$("#icos_startdt").val()+"&fromDate="+$("#icos_enddt").val()
    e.preventDefault();
    $.ajax({
      url : 'icos_put_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        console.log(result)
        $("#icos_put_info_cnt").html(comma(result.value))
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
        $('#loading').addClass('display-none')
      }
    });

    $.ajax({
      url : 'icos_post_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        console.log(result);
        if(result == 'null'){
          $("#icos_post_info_cnt").html(comma(0))
        }
        else{
          $("#icos_post_info_cnt").html(comma(result.value))
        }
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
        $('#loading').addClass('display-none')
      }
    });

    $.ajax({
      url : 'icos_copy_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        console.log(result);
        if(result == 'null'){
          $("#icos_copy_info_cnt").html(comma(0))
        }
        else{
          $("#icos_copy_info_cnt").html(comma(result.value))
        }
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
        $('#loading').addClass('display-none')
      }
    });

    $.ajax({
      url : 'icos_list_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        console.log(result);
        if(result == 'null'){
          $("#icos_list_info_cnt").html(comma(0))
        }
        else{
          $("#icos_list_info_cnt").html(comma(result.value))
        }
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
        $('#loading').addClass('display-none')
      }
    });

    $.ajax({
      url : 'icos_retrieval_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        console.log(result);
        if(result == 'null'){
          $("#icos_retrieval_info_cnt").html(comma(0))
        }
        else{
          $("#icos_retrieval_info_cnt").html(comma(result.value))
        }
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
        $('#loading').addClass('display-none')
      }
    });

    $.ajax({
      url : 'icos_get_info',
      dataType : 'json',
      type : 'POST',
      data : {
        msg : param
      },
      //async : false,
      success : function(result) {
        $("#icos_get_info_cnt").html(comma(result.value))
      }
      , beforeSend: function(){
         $('#loading').removeClass('display-none')
      }
      , complete : function(result) {
         $('#loading').addClass('display-none')
      }
    });

  });
});

$(document).ready(function () {
$.ajax({
  url : 'icos_info',
  dataType : 'json',
  type : 'GET',
  success : function(result) {
    var i = 0;
    var dev_objcnt;
    var alp_objcnt;
    var prd_objcnt;
    var dev_byteused;
    var alp_byteused;
    var prd_byteused;
    $.each(result, function(key, value) {
      if(result[i].name == "snapshot.11st.co.kr"){
        prd_byteused=result[i].bytesUsed;
        prd_objcnt=result[i].objectCount;
      }
      if(result[i].name == "alp.snapshot.11st.co.kr"){
        alp_byteused=result[i].bytesUsed;
        alp_objcnt=result[i].objectCount;
      }
      if(result[i].name == "dev.snapshot.11st.co.kr"){
        dev_byteused=result[i].bytesUsed;
        dev_objcnt=result[i].objectCount;
      }
      i++;
    });

    $("#snapshot_obj_cnt").html(comma(JSON.stringify(prd_objcnt)));
    $("#snapshot_byteused").html(comma(JSON.stringify(prd_byteused)));
    $("#alp_snapshot_obj_cnt").html(comma(JSON.stringify(alp_objcnt)));
    $("#alp_snapshot_byteused").html(comma(JSON.stringify(alp_byteused)));
    $("#dev_snapshot_obj_cnt").html(comma(JSON.stringify(dev_objcnt)));
    $("#dev_snapshot_byteused").html(comma(JSON.stringify(dev_byteused)));

  }
  , beforeSend: function(){
     $('#loading').removeClass('display-none')
  }
  , complete : function(result) {
     $('#loading').addClass('display-none')
  }
});
})
