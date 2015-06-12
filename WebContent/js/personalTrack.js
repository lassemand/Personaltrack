(function(){
	console.log('test');
	
	angular.module("personalTrack", ['ngMaterial'])
	.controller("ptController", ptController);
	
	function ptController($scope, $log){
		$log.log('Works');
	}
}())