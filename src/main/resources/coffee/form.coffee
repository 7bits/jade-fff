window.FormController = ($scope, $http) ->
  $scope.master= {}

  $scope.update = (user) ->
    $scope.master= angular.copy(user)
    $http({
      method : 'POST',
      url : '/create',
      data : $scope.master
    }).error( (data, status, headers, config) ->
        alert('hi!')
    ).success( (data, status, headers, config) ->
        window.location
    )


