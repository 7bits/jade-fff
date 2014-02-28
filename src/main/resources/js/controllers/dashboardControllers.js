recruitersControllers.controller('EmployerFeedback', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchDeals = function() {
            $http.get('employer-control-panel-feedback.json').success(function(data) {
                $scope.deals = data.entry.value;
            });
            $timeout(function(){$scope.fetchDeals()}, 10000);
        };

        $scope.fetchDeals();
    }
]);

recruitersControllers.controller('EmployerApplicants', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchApplicants = function() {
            $http.get('employer-control-panel-applicants.json').success(function(data) {
                $scope.applicants = data.entry.value;
            });
            $timeout(function(){$scope.fetchApplicants()}, 10000);
        };

        $scope.fetchApplicants();
    }
]);

recruitersControllers.controller('EmployerBids', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchBids = function() {
            $http.get('employer-control-panel-bids.json').success(function(data) {
                $scope.bids = data.entry.value;
            });
            $timeout(function(){$scope.fetchBids()}, 10000);
        };

        $scope.fetchBids();
    }
]);

recruitersControllers.controller('RecruiterFeedback', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchDeals = function() {
            $http.get('recruiter-control-panel-feedback.json').success(function(data) {
                $scope.deals = data.entry.value;
            });
            $timeout(function(){$scope.fetchDeals()}, 10000);
        };

        $scope.fetchDeals();
    }
]);

recruitersControllers.controller('RecruiterDeals', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchDeals = function() {
            $http.get('recruiter-control-panel-deals.json').success(function(data) {
                $scope.deals = data.entry.value;
            });
            $timeout(function(){$scope.fetchDeals()}, 10000);
        };

        $scope.fetchDeals();
    }
]);

recruitersControllers.controller('RecruiterBids', ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.fetchBids = function() {
            $http.get('recruiter-control-panel-bids.json').success(function(data) {
                $scope.bids = data.entry.value;
            });
            $timeout(function(){$scope.fetchBids()}, 10000);
        };

        $scope.fetchBids();
    }
]);
