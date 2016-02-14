jQuery(document).ready(function($){

	// Highlight the current chosen navigation button
	var currentURL = document.URL;
	var urlParts = currentURL.split("/");
	$('#' + urlParts[3].split("?")[0]).attr("class", "current");
	
	var logoutDishDialog = $("#dialog-form-logout").dialog({
		autoOpen : false,
		height : 150,
		width : 150,
		modal : true,
		buttons : {
		}
	});
	$("#logout-btn").on('click', function(event) {
		logoutDishDialog.dialog("open");		
	})
	
	var loginDishDialog = $("#dialog-form-login").dialog({
		autoOpen : false,
		height : 300,
		width : 300,
		modal : true,
		buttons : {
		}
	});
	$("#login-btn").on('click', function(event) {
		loginDishDialog.dialog("open");		
	})
	if($("#login-btn").length > 0) {
		loginDishDialog.dialog("open");
	}
	
	// Shopping cart check out button click. 
	$(".checkout-btn").on('click', function(event) {
		if($('#cd-cart .cd-cart-items li').length != 0) {
			$.ajax({
		        url: "/cart/checkout/",
		        type: 'post',
		        async: false,
		        datatype : "json",
		        success: function(data, status){
		        	displayCartItemsCount();
		        	$('#dialog-checkout-success').dialog({ 
		        		buttons: [
		        		          {text: checkoutDialogboxBtn1txt, click: function() {$(this).dialog("close")}},
		        		          {text: checkoutDialogboxBtn2txt, click: function() {window.location.href = "/salesRecord";}}
		        		          ], 
		        		modal: true, 
		        		autoOpen: false 
		        	}) .dialog("open");
		        }
			});
			reset_cart();			
		}
	});
	
	//if you change this breakpoint in the style.css file (or _layout.scss if you use SASS), don't forget to update this value as well
	var $L = 1200,
		$menu_navigation = $('#main-nav'),
		$cart_trigger = $('#cd-cart-trigger'),
		$hamburger_icon = $('#cd-hamburger-menu'),
		$lateral_cart = $('#cd-cart'),
		$shadow_layer = $('#cd-shadow-layer');

	//open lateral menu on mobile
	$hamburger_icon.on('click', function(event){
		event.preventDefault();
		//close cart panel (if it's open)
		$lateral_cart.removeClass('speed-in');
		toggle_panel_visibility($menu_navigation, $shadow_layer, $('body'));
	});
	
	displayCartItemsCount();
	$cart_trigger.on('click', cart_trigger_click);

	//close lateral cart or lateral menu
	$shadow_layer.on('click', function(){
		$shadow_layer.removeClass('is-visible');
		// firefox transitions break when parent overflow is changed, so we need to wait for the end of the trasition to give the body an overflow hidden
		if( $lateral_cart.hasClass('speed-in') ) {
			$lateral_cart.removeClass('speed-in').on('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
				$('body').removeClass('overflow-hidden');
			});
			$menu_navigation.removeClass('speed-in');
		} else {
			$menu_navigation.removeClass('speed-in').on('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
				$('body').removeClass('overflow-hidden');
			});
			$lateral_cart.removeClass('speed-in');
		}
	});

	//move #main-navigation inside header on laptop
	//insert #main-navigation after header on mobile
	move_navigation( $menu_navigation, $L);
	$(window).on('resize', function(){
		move_navigation( $menu_navigation, $L);
		
		if( $(window).width() >= $L && $menu_navigation.hasClass('speed-in')) {
			$menu_navigation.removeClass('speed-in');
			$shadow_layer.removeClass('is-visible');
			$('body').removeClass('overflow-hidden');
		}

	});
		
	$('.add-to-cart-button').click(function(e){
		var cart = $('#cd-cart-count');
        var imgtodrag = $(this).parent('.detail_info').parent('.detail-view').find("img").eq(0);
        if (imgtodrag) {
            var imgclone = imgtodrag.clone()
                .offset({
                top: imgtodrag.offset().top,
                left: imgtodrag.offset().left
            })
                .css({
                'opacity': '0.5',
                    'position': 'absolute',
                    'height': '500px',
                    'width': '500px',
                    'z-index': '100'
            })
                .appendTo($('body'))
                .animate({
                'top': cart.offset().top + 10,
                    'left': cart.offset().left + 10,
                    'width': 75,
                    'height': 75
            }, 1000, 'easeInOutExpo');
            
            setTimeout(function () {
                cart.effect("shake", {
                    times: 2
                }, 200);
            }, 1500);

            imgclone.animate({
                'width': 0,
                    'height': 0
            }, function () {
                $(this).detach()
            });
        }
		
		var thisID 	  = $(this).parent().parent().attr('id').replace('detail-','');
		$.ajax({
	        url: "/cart/add/",
	        type: 'post',
	        data: {menuItemId: thisID},
	        async: false,
	        datatype : "json",
	        success: function(data, status){
	        	if(data.Result != "OK") {
		    		alert("Result: " + data.Result + "\nMessage: " + data.message + "\nStatus: " + status);
		    	}
	        }
		});
		displayCartItemsCount();
	});	
	
	
	// this is for 2nd row's li offset from top. It means how much offset you want to give them with animation
	var single_li_offset 	= 200;
	var current_opened_box  = -1;
	
	$('#wrap li').click(function() {
	
		var thisID = $(this).attr('id');
		var $this  = $(this);
		
		var id = $('#wrap li').index($this);
		
		if(current_opened_box == id) // if user click a opened box li again you close the box and return back
		{
			$('#wrap .detail-view').slideUp('fast');
			current_opened_box  = -1;
			return false;
		}
		$('#cart_wrapper').slideUp('fast');
		$('#wrap .detail-view').slideUp('fast');
		
		// save this id. so if user click a opened box li again you close the box.
		current_opened_box = id;
		
		var targetOffset = 0;
		
		// below conditions assumes that there are four li in one row and total rows are 4. How ever if you want to increase the rows you have to increase else-if conditions and if you want to increase li in one row, then you have to increment all value below. (if(id<=3)), if(id<=7) etc
		
		if(id<=3)
			targetOffset = 0;
		else if(id<=7)
			targetOffset = single_li_offset;
		else if(id<=11)
			targetOffset = single_li_offset*2;
		else if(id<=15)
			targetOffset = single_li_offset*3;
		
		$("html:not(:animated),body:not(:animated)").animate({scrollTop: targetOffset}, 150,function(){
			
			$('#wrap #detail-'+thisID).slideDown(200);
			return false;
		});
		
	});
	
	$('.close a').click(function() {
		
		$('#wrap .detail-view').slideUp('fast');
		
	});
	

	function reset_cart() {
		$('#cd-cart .cd-cart-items li').remove();
		$('.cd-cart-items').css({height:0});
		$(".cd-cart-total p span")[0].textContent = 0;
		displayCartItemsCount();
	}
	
	function change_cart_item_qty(event) {
		var thisID = $(this).parent().parent().attr('id');
		var qty = $(this).val();
		$.ajax({
		    url: "/cart/changeQTY?itemId=" + thisID + "&qty=" + qty,
		    type: 'post',
		    async: true,
		    datatype : "json",
		    success: function(data, status){
		    	if(data.Result != "OK") {
		    		alert("Result: " + data.Result + "\nMessage: " + data.message + "\nStatus: " + status);
		    		return;
		    	}
		    	open_cart(event);
	        }
		});
	}
	
	function remove_cart_item(event) {
		var thisID = $(this).parent().attr('id');
		$.ajax({
		    url: "/cart/delete/" + thisID,
		    type: 'post',
		    async: true,
		    datatype : "json",
		    success: function(data, status){
		    	if(data.Result != "OK") {
		    		alert("Result: " + data.Result + "\nMessage: " + data.message + "\nStatus: " + status);
		    		return;
		    	}
		    	open_cart(event);
	        }
		});
	}
	
	function cart_trigger_click(event) {
		var $menu_navigation = $('#main-nav');
		var $lateral_cart = $('#cd-cart');
		var $shadow_layer = $('#cd-shadow-layer');

		event.preventDefault();
		//close lateral menu (if it's open)
		$menu_navigation.removeClass('speed-in');
		var isVisible = toggle_panel_visibility($lateral_cart, $shadow_layer, $('body'));
		if(!isVisible) 
			return;
		open_cart(event);
	}
	
	function open_cart(event){ // jquery has a bug here: open_cart defined out side of ready(), $.ajax data is not json. 
		$.ajax({
		    url: "/cart/get/",
		    type: 'get',
		    async: false,
		    datatype : "json",
		    success: function(data, status){
		    	if(data.Result != "OK") {
		    		alert("Result: " + data.Result + "\nMessage: " + data.message + "\nStatus: " + status);
		    		return;
		    	}
		    	
		    	reset_cart();
	    		
	    		var prev_charges = 0;
	    		for(var i = 0; i < data.Records.length; i++) {
	    			var itemid = data.Records[i].id;
		    		var itemname = data.Records[i].menuItemTitle;
		    		var itemprice = data.Records[i].menuItemPrice;
		    		var itemquantity= data.Records[i].quantity;
		    		
					prev_charges += parseInt(itemprice * itemquantity);
					$('.cd-cart-total p span').html(prev_charges);
					
					var Height = $('.cd-cart-items').height();
					$('.cd-cart-items').css({height:Height+parseInt(55)});
					
					$('#cd-cart .cd-cart-items').append(
						'<li id="' + itemid + '">' +
							itemname + '(￥' + itemprice + ')' +
							'<span class="cd-qty">' + 
								' x <input class="cart-item-qty" type="number" onchange="" min="0" value="' + itemquantity + '"style="display: inline-block; width: 35px; min-width: 35px;">' + 
							'</span>' + 
							'<img class="cd-item-remove cd-img-replace"></img>'+
						'</li>');
					
	    		}
	    		$(".cd-item-remove").on('click', remove_cart_item);
	    		$(".cart-item-qty").on('change', change_cart_item_qty);
		    }
		});
		displayCartItemsCount();
	}
});

function toggle_panel_visibility ($lateral_panel, $background_layer, $body) {
	if( $lateral_panel.hasClass('speed-in') ) {
		// firefox transitions break when parent overflow is changed, so we need to wait for the end of the trasition to give the body an overflow hidden
		$lateral_panel.removeClass('speed-in').one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
			$body.removeClass('overflow-hidden');
		});
		$background_layer.removeClass('is-visible');
		return false;

	} else {
		$lateral_panel.addClass('speed-in').one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
			$body.addClass('overflow-hidden');
		});
		$background_layer.addClass('is-visible');
		return true;
	}
}

function move_navigation( $navigation, $MQ) {
	if ( $(window).width() >= $MQ ) {
		$navigation.detach();
		$navigation.appendTo('header');
	} else {
		$navigation.detach();
		$navigation.insertAfter('header');
	}
}

function include(arr, obj) {
  for(var i=0; i<arr.length; i++) {
    if (arr[i] == obj) return true;
  }
}

function getpos(arr, obj) {
  for(var i=0; i<arr.length; i++) {
    if (arr[i] == obj) return i;
  }
}

function clickNav(id) {
	$('.current').removeAttr("class");
	if(id == "coldDishes" || id == "admin") 
		window.location.href = "../" + id + "/1"; // category id begins from 1.
	else
		window.location.href = "../" + id;
}

function isAdmin() {
	if($("#accountInfo")[0].getAttribute("class") == "NO")
		return false;
	else
		return true;
}

function displayCartItemsCount() {
	$.get("/cart/getCount", function(data) {
		cart = $('#cd-cart-count');
		if(data > 0) {
			(!cart.hasClass('items-added')) && cart.addClass('items-added'); 
		} else {
			(cart.hasClass('items-added')) && cart.removeClass('items-added'); 
		}
		var cartItems = cart.find('span');
		cartItems.text(data);
	});
}