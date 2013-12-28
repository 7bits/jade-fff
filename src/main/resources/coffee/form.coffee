# angular.module('myApp', []).service('formService', () ->
#   return {
#     save = (formData, url) ->
#       $http({
#         method : 'POST',
#         url : url,
#         data : formData
#       }).error( (data, status, headers, config) ->
#           alert('hi!')
#       ).success( (data, status, headers, config) ->
#           window.location
#       )
#   }
# )


#window.FormController = ($scope) ->

  #$scope.submit = () ->
    #alert(angular.toJson($scope.applicantForm))

  #$scope.update = (user) ->
    #formService.save(user, 'employer-profile')
    #$scope.master= angular.copy(user)
    # $http({
    #   method : 'POST',
    #   url : '/create',
    #   data : $scope.master
    # }).error( (data, status, headers, config) ->
    #     alert('hi!')
    # ).success( (data, status, headers, config) ->
    #     window.location
    # )



window.easyHunt = angular.module('easyHunt', []);

easyHunt.controller('EmployerProfileController', ['$scope',  ($scope) ->
  $scope.submit = () ->
    alert(angular.toJson($scope.employerProfileForm))
]);
