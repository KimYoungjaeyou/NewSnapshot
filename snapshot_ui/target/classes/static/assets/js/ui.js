$(document).ready(function() { // ready function start

	// con-group
	$('.con-group').each(function() {
		var groups = $(this),
				btns   = groups.find('.btn');

		btns.click(function() {
			$(this).addClass('active').siblings('.btn').removeClass('active');
		});
	});

	// tabs
	$(".tabs").each(function() {
		var tabs     = $(this),
				anchors  = tabs.find('a[href^="#tabs"]');

			anchors.each(function() {
				var triggers = $(this),
						targets  = $(triggers.attr('href'));

				$(this).on('click', function(event) {
					event.preventDefault();

					$(this).addClass('active').parents('li').siblings().children('.tab-triggers').removeClass('active');
					targets.addClass('active').siblings().removeClass('active');
				});
			});
	});


	// select
	basicSelect();
	designSelect();
//	userSelect();

}); // ready function end



// select
function basicSelect() {
	var selects = $('.select'),
			targets = selects.children('select');

	targets.on({
		'focus': function() {
			//$(this).parents('.select').addClass('focus');
		},
		'blur': function() {
			//$(this).parents('.select').removeClass('focus');
			$(this).parents('.select').removeClass('active');
		}
	});
	targets.change(function() {
		var selected = $(this).children('option:selected').text();

		$(this).siblings('.trigger').children('span').text(selected);
		$(this).parent().removeClass('focus');
	});
	targets.on('click', function() {
		$(this).parents('.select').toggleClass('active');
	});
}
function designSelect() {
	var selects  = $('.select'),
			triggers = selects.children('.trigger'),
			targets  = selects.children('.target'),
			labels   = targets.find('label');

	selects.on('mouseleave', function() {
		$(this).removeClass('active');
	});
	triggers.on('click', function(event) {
		event.preventDefault();

		$(this).parents('.select').toggleClass('active');
	});
	labels.on('click', function() {
		var selected = $(this).text();

		$(this).addClass('selected').parents('li').siblings().children('label').removeClass('selected');
		$(this).parents('.select').children('.trigger').find('span').text(selected);
		$(this).parents('.select').removeClass('active');
	});
}


// layers
$('a[href^="#layer"]').each(function() {
	var triggers = $(this),
			targets  = $(triggers.attr('href'));

	triggers.on('click', function(event) {
		event.preventDefault();
		targets.fadeIn(200);
		//$('<div class="mask"></div>').appendTo(targets);
	});
	targets.find('.layer-close').on('click', function() {
		targets.fadeOut(100);
		//targets.find('.mask').remove();
	});
});
