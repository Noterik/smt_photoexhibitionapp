var Mainscreen = function(options) { 
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	
	$("#photo > img").on("load", function(){ 
	    console.log("Image loaded");
	});
	
	self.putMsg = function(msg) {
		try{
			var command = [msg.target[0].class];
		}catch(e){
			command = $(msg.currentTarget).attr('class').split(" ");
		}
		
		var content = msg.content;
		for(var i=0; i<command.length; i++) {
			switch(command[i]) { 
				case 'resizeImage':
					resizeImage();
					break;
			}
		}
	}
	return self;
}
	
function resizeImage() {	
		var bodyWidth = $(window).width();
		var bodyHeight = $(window).height();
		var imgWidth = $("#photo > img").width();
		var imgHeight = $("#photo > img").height();

		var widthFactor =  bodyWidth / imgWidth;
		var heightFactor = bodyHeight / imgHeight;

		if (widthFactor < heightFactor) {
			$("#photo > img").width(bodyWidth-2);
			$("#photo > img").height(widthFactor * imgHeight);
		} else {
			$("#photo > img").width(heightFactor * imgWidth);
			$("#photo > img").height(bodyHeight-2);							
		}
		$("#photo > img").css("display", "block");
}
	