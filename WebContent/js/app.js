(function(){
	"use strict"
	
	angular.module("galleryApp", [])
	.controller("galleryController", galleryController);
	
	function galleryController($scope, $log){
		var request = new XMLHttpRequest();
		var path = 'http://localhost:8080/PersonalTrack/rest/map/allmaps';
		request.open("GET", path, false);
		request.onreadystatechange = function(){
			var maps = $.parseJSON(request.responseText);
			$scope.photos = maps;
		};
		request.send(null);
		
		var rootUrl = "http://localhost:8080/PersonalTrack/rest/";

		var httpPost = function(url){
			var request = new XMLHttpRequest();
			var path = rootUrl + url;
			request.open("POST", path, true);
			request.setRequestHeader("Accept", "application/json");
			request.setRequestHeader("Content-type", "application/json");
			return request;
		}

		var httpPut = function (url) {
			var request = new XMLHttpRequest();
			var path = rootUrl + url;
			request.open("PUT", path, true);
			request.setRequestHeader("Accept", "application/json");
			request.setRequestHeader("Content-type", "application/json");
			return request;
		}
		
		var addNewMap = function (mymap) {
			
			var mymaps = $scope.photos
			var photos = new Array(mymaps.length +1);
			for(var i = 0; i<mymaps.length; i++){
				var map = mymaps[i];
				photos[i] = map;
			}
			photos[mymaps.length] = mymap;
			$scope.photos = photos;
			return mymaps.length;
		}
		
		var removeNewMap = function (index) {
			
			var mymaps = $scope.photos;
			;
			mymaps.splice(index, 1);
			$scope.photos = mymaps;
			$scope.$apply();
		}
		
		// initial image index
		$scope._Index = 0;

		// if a current image is the same as requested image
		$scope.isActive = function(index) {

			return $scope._Index === index;
		};

		// show prev image
		$scope.showPrev = function() {
			$scope._Index = ($scope._Index > 0) ? --$scope._Index
					: $scope.photos.length - 1;
		};

		// show next image
		$scope.showNext = function() {
			$scope._Index = ($scope._Index < $scope.photos.length - 1) ? ++$scope._Index
					: 0;
		};

		// show a certain image
		$scope.showPhoto = function(index) {
			$scope._Index = index;
		};
		
		  function handleFileSelect(evt) {
			    var files = evt.target.files; // FileList object
			    var file = files[0];
			    if (files && file) {
			    	
			        var reader = new FileReader();

			        reader.onload = function(readerEvt) {
			            var binaryString = readerEvt.target.result;
			            var map = {
			            	    "name": "Test",
			            	    "size": 10000,
			            	    "image": btoa(binaryString)
			            	}
			            var addedIndex = addNewMap(map);
			            $scope.$apply();
			            var request = httpPost('map');
			            request.send(JSON.stringify(map));
			    		request.onreadystatechange = function(){
			    			console.log('Status' + request.status);
				            if(request.status != 200)
				            {
				            	console.log('REMOVE!' + addedIndex);
				            	removeNewMap(addedIndex);
				            }
			    		};
			            

			        };
			        reader.readAsBinaryString(file);
			    }
			};

			  document.getElementById('files').addEventListener('change', handleFileSelect, false);
			  $log.log("files loaded");
	}
}())

