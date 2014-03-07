recruitersControllers.controller('RecruiterBidsCtrl', ['$scope', '$http',
    function ($scope, $http) {
        $scope.formData ={};
        var appUrl = $(document).find("head").find("base").attr("href");

        $scope.$on("formChange", function() {
            $http({
                method: 'POST',
                params: $scope.formData,
                url: appUrl + 'recruiter-bids-filter-ajax.json'
            }).success(function(data) {
                    $scope.$emit("recruiterBids", data.entry.value);
                });
        });
    }
]);

recruitersControllers.controller('RecruiterBidsTableCtrl', ['$scope', 'ngTableParams',
    function ($scope, ngTableParams) {
        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 999,
            recruiterBids: {}
        }, {
            total: 0,
            counts: [],
            getData: function($defer, params) {
                $scope.formData.sortColumn = Object.keys(params.sorting)[0];
                if (Object.values(params.sorting)[0] === "desc") {
                    $scope.formData.sortAsc = 0;
                } else {
                    $scope.formData.sortAsc = 1;
                }
                $defer.resolve(params.$params.recruiterBids);
            }
        });
        $scope.$parent.$on("recruiterBids", function(event, recruiterBids) {
            $scope.tableParams.$params.recruiterBids = recruiterBids;
        });
    }
]);