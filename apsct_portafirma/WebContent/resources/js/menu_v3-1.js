$(document).ready(function(){
	var $menu = $(".menuButton")
	,	$document = $( document )
	,	$content = $("#content")
	,	$headderNav = $("#header-nav")	
	,	$search = $(".searchButton")
	,	$action = $("#action")
	,	$actionTittle = $("action-title")
	,	$menuSearchNav = $('#menu-search-nav')
	,	$menuActionNav = $("#menu-action-nav")
	,	$buttonSearch = $('#button-search')
	,	$bodySearch = $('.body-search')
	,	rounded = 'rounded'
	,	$order = $('#order');
	
	var size = "30px";
	
	$("input").click(function(event){
	    event.stopPropagation();
	});
	
	$( window ).resize(function() {
		$bodySearch.show();
		$menuActionNav.hide();
		$menuSearchNav.hide();
		$menu.removeClass('iconMenuActive');
		$search.removeClass('iconMenuActive');
	});
	
	$buttonSearch.hide();
	
	$content.click(function(){
		$menuSearchNav.hide();
		$menuActionNav.hide();
		hideOrderMenu();
		$menu.removeClass('iconMenuActive');
		$search.removeClass('iconMenuActive');
	});
	
	$menu.click( function(){
		if($('#menu-action-nav').is(':visible')){
			$menu.removeClass('iconMenuActive');
		} else {
			$menu.addClass('iconMenuActive');
			$search.removeClass('iconMenuActive');
		}
		$search.css('width',size);
		$menuActionNav.css("height",(window.innerHeight -200)+'px');
		$menuActionNav.toggle('slide',{ direction:'left'},500);
		$menuSearchNav.hide();

	});
	
	$search.click( function(){
		if($menuSearchNav.is(':visible')){
			$search.removeClass('iconMenuActive');
		} else {
			$search.addClass('iconMenuActive');
			$menu.removeClass('iconMenuActive');
		}
		hideOrderMenu()
		$menu.css('width',size);
		$menuSearchNav.css("height",(window.innerHeight-200) +'px');
		$buttonSearch.toggle('slide',{direction:'right'},300);
		$menuSearchNav.toggle('slide',{ direction:'right'},500);
		$menuActionNav.hide();

	});
	
	$order.click(function(){
		if ($('#menu-order-nav').is(':visible')){
			hideOrderMenu();
		} else{
			$('#menu-order-nav').show();
		}
	});
});

function expandMenu(element){
	$('#menu-action-nav').toggle();
	$(".menuButton").removeClass('iconMenuActive');
	$(".searchButton").removeClass('iconMenuActive');	
}

function menuFilter(){
	$('.header-search').siblings().toggle();
	var icon = $('.buttonFiltros').find("i");
	if ($(icon).hasClass('fa-plus-circle')){
		$(icon).removeClass('fa-plus-circle').addClass('fa-minus-circle');
	} else {
		$(icon).removeClass('fa-minus-circle').addClass('fa-plus-circle');
	}
}

function hideOrderMenu(){
	$('#menu-order-nav').hide();
}



