recruitersDirectives.directive("bidWithdraw", ['$http', function($http) {
    return {
        scope: {
            bidId: '@bidWithdraw'
        },
        controller: function($http, $scope) {
            appUrl = $(document).find("head").find("base").attr("href");
            $scope.bidWithdraw = function() {
                $http({
                    method: 'GET',
                    params: {bidId: $scope.bidId},
                    url: appUrl + 'recruiter-bid-withdraw.json'
                }).success(function(data) {
                        var withdrawLabel = data.entry.value;
                        $scope.$emit('withdrawLabel', withdrawLabel);
                    });
            }
        },
        link: function(scope, element, attrs) {
            element.on("click", function(event) {
                event.preventDefault();
                scope.bidWithdraw();
                scope.$on('withdrawLabel', function(event, withdrawLabel){
                    $(event.target).html(withdrawLabel);
                    $(event.target).prop("disabled", true);
                });
            });
        }
    }
}]);