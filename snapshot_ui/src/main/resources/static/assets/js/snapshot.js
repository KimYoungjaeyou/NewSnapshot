//api 조회
$(function(){
	$("#api_count_search").click(function(e){
    e.preventDefault();
		var sdt = $("#api_startdt").val()
		var edt = $("#api_enddt").val()
    if(sdt == ''){
      $('#error_handle').removeClass('display-none')
        return false;
    }else if(edt == ''){
      $('#error_handle').removeClass('display-none')
        return false;
    }

		$('#api_summary_count').find('td').each(function(i,e){
			var data = { startdt : sdt , enddt : edt, tdid : $(this).attr('id')	}
			if($(this).attr('id')){
				$.ajax({
					url : "api_summary_count",
					type : "GET",
          data : data,
					success: function(data){
  						 $('#'+data.tdid).html(comma(data.count))
               if ( data.error )
               {
                   $('#error_handle').removeClass('display-none')
                   $('#'+data.tdid).html("")
                   $('.api_create_total_success_ratio').html("")
                   $('.api_create_total_fail_ratio').html("")
                   $('.api_result_total_success_ratio').html("")
                   $('.api_result_total_fail_ratio').html("")
                   $('.api_delete_total_success_ratio').html("")
                   $('.api_delete_total_fail_ratio').html("")
              }
					}
          , beforeSend: function(){
             $('#loading').removeClass('display-none')
          }
          , complete:function(){
            var cnt1 = uncomma($('#api_create_total_cnt').html())
            var cnt2 = uncomma($('#api_create_total_success_cnt').html())
            var cnt3 = uncomma($('#api_create_total_fail_cnt').html())
            var cnt4 = uncomma($('#api_result_total_cnt').html())
            var cnt5 = uncomma($('#api_result_total_success_cnt').html())
            var cnt6 = uncomma($('#api_result_total_fail_cnt').html())
            var cnt7 = uncomma($('#api_delete_total_cnt').html())
            var cnt8 = uncomma($('#api_delete_total_success_cnt').html())
            var cnt9 = uncomma($('#api_delete_total_fail_cnt').html())
              if ( cnt1 != null && cnt2 != null && cnt3 != null && cnt4 != null &&
                cnt5 != null && cnt6 != null && cnt7 != null && cnt8 != null && cnt9 != null ){

                  if ( cnt1 != 0 ){
                    var result1 = cnt2 / cnt1 * 100
                    var result2 = cnt3 / cnt1 * 100
                    $('.api_create_total_success_ratio').html(result1.toFixed(2)+'%')
                    $('.api_create_total_fail_ratio').html(result2.toFixed(2)+'%')
                  }
                  if ( cnt4 != 0 ){
                    var result1 = cnt5 / cnt4 * 100
                    var result2 = cnt6 / cnt4 * 100
                    $('.api_result_total_success_ratio').html(result1.toFixed(2)+'%')
                    $('.api_result_total_fail_ratio').html(result2.toFixed(2)+'%')
                  }
                  if ( cnt7 != 0 ){
                    var result1 = cnt8 / cnt7 * 100
                    var result2 = cnt9 / cnt7 * 100
                    $('.api_delete_total_success_ratio').html(result1.toFixed(2)+'%')
                    $('.api_delete_total_fail_ratio').html(result2.toFixed(2)+'%')
                  }
            $('#loading').addClass('display-none')
            }
				}

			})
		}})
		$('#api_error_count').find('td').each(function(i,e){
			var data = { startdt : sdt , enddt : edt, tdid : $(this).attr('id')	}
			if($(this).attr('id')){
				$.ajax({
					url : "api_error_count",
					type : "GET",
					data : data,
					success: function(data){
						     $('#'+data.tdid).html(comma(data.count))
                 if ( data.error )
                 {
                     $('#'+data.tdid).html("")
                }
					}
        })
			}
		})
	})
})



$(function(){
	$("#ss_count_search").click(function(e){
  		e.preventDefault();
  		var sdt = $("#ss_startdt").val()
  		var edt = $("#ss_enddt").val()
      if(sdt == ''){
        $('#error_handle').removeClass('display-none')
          return false;
      }else if(edt == ''){
        $('#error_handle').removeClass('display-none')
          return false;
      }

		$('#ss_summary_count').find('td').each(function(i,e){
			var data = { startdt : sdt , enddt : edt, tdid : $(this).attr('id')	}
			if($(this).attr('id')){
				$.ajax({
					url : "ss_summary_count",
					type : "GET",
					data : data,
					success: function(data){
              $('#'+data.tdid).html(comma(data.count))
              if ( data.error )
              {
                  $('#error_handle').removeClass('display-none')
                  $('#'+data.tdid).html("")
                  $('.ss_create_total_success_ratio').html("")
                  $('.ss_create_total_fail_ratio').html("")
                  $('.ss_delete_total_success_ratio').html("")
                  $('.ss_delete_total_fail_ratio').html("")
             }
					}
          , beforeSend: function(){
             $('#loading').removeClass('display-none')
          }
          , complete:function(){
            //추후 삭제로그 추가 예정
            var cnt1 = uncomma($('#ss_create_total_cnt').html())
            var cnt2 = uncomma($('#ss_create_total_success_cnt').html())
            var cnt3 = uncomma($('#ss_create_total_fail_cnt').html())
            if ( cnt1 != null && cnt2 != null && cnt3 != null )
                {
                       if (cnt1 != 0){
                       var result1 = uncomma(cnt2) / cnt1 *100
                       var result2 = cnt3 / cnt1 *100
                       $('.ss_create_total_success_ratio').html(result1.toFixed(2)+'%')
                       $('.ss_create_total_fail_ratio').html(result2.toFixed(2)+'%')
                       }
            $('#loading').addClass('display-none')
          }
          }
				})
			}
		})
		$('#ss_error_count').find('td').each(function(i,e){
			var data = { startdt : sdt , enddt : edt, tdid : $(this).attr('id')	}
			if($(this).attr('id')){
				$.ajax({
					url : "ss_error_count",
					type : "GET",
					data : data,
					success: function(data){
            $('#'+data.tdid).html(comma(data.count))
            if ( data.error )
            {
                $('#'+data.tdid).html("")
           }
					}
				})
			}
		})

	})
})
