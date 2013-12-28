'use strict';

window.easyHuntControllers = angular.module('easyHunt.controllers', [])


easyHuntControllers = easyHunt.controller('EmployerProfileController', ['$scope', '$http', ($scope, $http) ->
  $scope.submit = () ->
    formData = angular.toJson($scope.employerProfileForm)
    $http({
      method : 'POST',
      url : '/create',
      data : formData
    }).error( (data, status, headers, config) ->
      alert('hi!')
    ).success( (data, status, headers, config) ->
      window.location.reload()
    )
]);