recruitersDirectives.directive("employerBid", ['$http', function($http) {
    return {
        scope: {
            bidId: '@employerBid'
        },
        controller: function($http, $scope) {
            appUrl = $(document).find("head").find("base").attr("href");
            $scope.fetchBid = function() {
                $http({
                    method: 'GET',
                    params: {bidId: $scope.bidId},
                    url: appUrl + 'employer-recruiter-show.json'
                }).success(function(data) {
                        var bid = data.entry.value;
                        $scope.$emit('employerBid', bid);
                    });
            }
        },
        link: function(scope, element, attrs) {
            element.popover({
                placement: "bottom",
                html: true,
                content: "",
                trigger: "manual"
            });
            element.on("mouseenter", function() {
                scope.fetchBid();
                scope.$on('employerBid', function(event, bid){
                    var html = "<dl class=\"dl popup\"><dt>" + bid.headRecruiter + "</dt><dd><a href=\"" + bid.recruiterUrl + "\" target=\"blank\">" + bid.recruiter + "</a></dd><dt>" + bid.headStatus + "</dt><dd>" + bid.status + "</dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headMessage + "</dt><dd>" + bid.message + "</dd>" + "</dl>";
                    var title = bid.popupTitle;
                    var popover = element.data("bs.popover");
                    popover.options.content = html;
                    popover.options.title = title;
                    popover.setContent();
                    popover.show();
                    if (element.children("strong").length != 0) {
                        element.html(element.find("strong").html());
                    }
                });
            });
            element.on("mouseleave", function() {
                var popover = element.data("bs.popover");
                window.timeoutObj = setTimeout(function() {
                    popover.hide();
                }, 350);
            });
            element.on("click", function(event) {
                event.preventDefault();
            });
        }
    }
}]);

recruitersDirectives.directive("employerApplicant", ['$http', function($http) {
    return {
        scope: {
            applicantId: '@employerApplicant'
        },
        controller: function($http, $scope) {
            appUrl = $(document).find("head").find("base").attr("href");
            $scope.fetchApplicant = function() {
                $http({
                    method: 'GET',
                    params: {applicantId: $scope.applicantId},
                    url: appUrl + 'employer-applicant-show.json'
                }).success(function(data) {
                        var applicant = data.entry.value;
                        $scope.$emit('employerApplicant', applicant);
                    });
            }
        },
        link: function(scope, element, attrs) {
            element.popover({
                placement: "bottom",
                html: true,
                content: "",
                trigger: "manual"
            });
            element.on("mouseenter", function() {
                scope.fetchApplicant();
                scope.$on('employerApplicant', function(event, applicant){
                    var ajaxData = "<dl class=\"dl popup\"><dt>" + applicant.headApplicant + "</dt><dd>" + applicant.applicant + "</dd><dt>" + applicant.headCreated + "</dt><dd>" + applicant.created + "</dd><dt>" + applicant.headGender + "</dt><dd>" + applicant.gender + "</dd><dt>" + applicant.headAge + "</dt><dd>" + applicant.age + "</dd>";
                    if (typeof applicant.resume != "undefined") {
                        ajaxData += "<dt>" + applicant.headResume + "</dt><dd><a href=\"" + applicant.resumeUrl + "\">" + applicant.resume + "</a></dd>";
                    }
                    if (typeof applicant.testFile != "undefined") {
                        ajaxData += "<dt>" + applicant.headTestAnswer + "</dt><dd><a href=\"" + applicant.testAnswerUrl + "\">" + applicant.testAnswer + "</a></dd>";
                    }
                    ajaxData += "<dt>" + applicant.headDescription + "</dt><dd>" + applicant.description + "</dd>" + "</dl>"
                    var title = applicant.popupTitle;
                    var popover = element.data("bs.popover");
                    popover.options.content = ajaxData;
                    popover.options.title = title;
                    popover.setContent();
                    popover.show();
                    if (element.children("strong").length != 0) {
                        element.html(element.find("strong").html());
                    }
                });
            });
            element.on("mouseleave", function() {
                var popover = element.data("bs.popover");
                window.timeoutObj = setTimeout(function() {
                    popover.hide();
                }, 350);
            });
            element.on("click", function(event) {
                event.preventDefault();
            });
        }
    }
}]);

recruitersDirectives.directive("recruiterBid", ['$http', function($http) {
    return {
        scope: {
            bidId: '@recruiterBid'
        },
        controller: function($http, $scope) {
            $scope.fetchBid = function() {
                $http({
                    method: 'GET',
                    params: {bidId: $scope.bidId},
                    url: 'recruiter-bid-show.json'
                }).success(function(data) {
                        var bid = data.entry.value;
                        $scope.$emit('recruiterBid', bid);
                    });
            }
        },
        link: function(scope, element, attrs) {
            element.popover({
                placement: "bottom",
                html: true,
                content: "",
                trigger: "manual"
            });
            element.on("mouseenter", function() {
                scope.fetchBid();
                scope.$on('recruiterBid', function(event, bid){
                    var ajaxData = "<dl class=\"dl popup\">";
                    if (typeof bid.deal != 'undefined') {
                        ajaxData += "<dt>" + bid.headDeal + "</dt><dd><a href=\"" + bid.dealUrl + "\">" + bid.deal + "</a></dd>";
                    }
                    ajaxData += "<dt>" + bid.headVacancy + "</dt><dd>" + bid.vacancy + "</dd><dt>" + bid.headSalary + "</dt><dd>" + bid.salary + "</dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headUpdated + "</dt><dd>" + bid.updated + "</dd><dt>" + bid.headExpiration + "</dt><dd>" + bid.expiration + "</dd><dt>" + bid.headStatus + "</dt><dd>" + bid.status + "</dd><dt>" + bid.headDescription + "</dt><dd>" + bid.description + "</dd>";
                    var title = bid.popupTitle;
                    var popover = element.data("bs.popover");
                    popover.options.content = ajaxData;
                    popover.options.title = title;
                    popover.setContent();
                    popover.show();
                });
            });
            element.on("mouseleave", function() {
                var popover = element.data("bs.popover");
                window.timeoutObj = setTimeout(function() {
                    popover.hide();
                }, 350);
            });
            element.on("click", function(event) {
                event.preventDefault();
            });
        }
    }
}]);
