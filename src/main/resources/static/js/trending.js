//<![CDATA[
    // run google trends API
    function runGoogleTrends()
    {
        var params = {
            searchTerm : '"' + document.getElementById("searchParam").elements[0].value + '"',
            startDate : document.getElementById("searchParam").elements[1].value,
            endDate : document.getElementById("searchParam").elements[2].value
        }
        var url = "google_trends" + formatParams(params);
        var EorK = isEnglish(document.getElementById("searchParam").elements[0].value);
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("container").innerHTML = '&nbsp;';
                document.getElementById("container").innerHTML = '<canvas id="related"></canvas><canvas id="rising"></canvas><div id="clear"></div>';
                formatDataForChart(this.responseText, EorK);

            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
        return false;
    }

    //format related queries data received from runGoogleTrends function.
    function formatDataForChart(data, EorK)
    {
        var splitData = data.split("splitHereForList");
        splitData[0] = JSON.parse(splitData[0]);
        splitData[1] = JSON.parse(splitData[1]);

        var chartData = [];
        for (var key in splitData)
        {
            if (splitData.hasOwnProperty(key))
            {
                var tempHolderLabels = [];
                var tempHolderValues = [];
                for (var key2 in splitData[key])
                {
                    if (splitData[key].hasOwnProperty(key2))
                    {
                        tempHolderLabels.push(splitData[key][key2].query);
                        tempHolderValues.push(splitData[key][key2].value);
                    }
                }
                chartData.push([tempHolderLabels, tempHolderValues]);
            }
        }

        drawChart(chartData, EorK);
    }

    //create bar chart using chart.js
    function drawChart(data, EorK) {
        var relatedChartTitle;
        var risingChartTitle;

        if (EorK)
        {
            relatedChartTitle = "Users who searched the term " + '"' + document.getElementById("searchParam").elements[0].value + '"' + " also searched: ";
            risingChartTitle = "Users who searched the term " + '"' + document.getElementById("searchParam").elements[0].value + '"' + " are searching these terms in increased frequency: ";
        } else {
            relatedChartTitle = '"' + document.getElementById("searchParam").elements[0].value + '"' + "을(를) 검색한 사람들이 많이 찾은 검색어";
            risingChartTitle = '"' + document.getElementById("searchParam").elements[0].value + '"' + "을(를) 검색한 사람들이 연이어 많이 검색하는 검색어";

        }
        var related = data[0];
        var rising = data[1];

        var ctxRelated = document.getElementById("related").getContext('2d');
        ctxRelated.canvas.width = window.innerWidth;
        ctxRelated.canvas.height = window.innerHeight - 100;

        var ctxRising = document.getElementById("rising").getContext('2d');
        ctxRising.canvas.width = window.innerWidth;
        ctxRising.canvas.height = window.innerHeight - 100;

        var background = [];
        var border = [];
        var num_data_point = related[1].length;
        var red = 255;
        var blue = 0;
        var offset = ~~(255/num_data_point);
        for (var i = 0; i < num_data_point; i++)
        {
            background.push('rgba(' + red.toString() + ',0,' + blue.toString() + ',0.5)');
            border.push('rgba(' + red.toString() + ',0,' + blue.toString() + ',1)');
            red -= offset;
            blue += offset;

        }

        var relatedChart = new Chart(ctxRelated, {
            type: 'bar',
            data: {
                labels: related[0],
                datasets: [{
                    label: '100 = 최고 인기 검색어 (100 = Most popular)',
                    data: related[1],
                    backgroundColor: background,
                    borderColor: border,
                    borderWidth: 1
                    }]
            },
            options: {
                title: {
                    display: true,
                    text: relatedChartTitle
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });

        var risingChart = new Chart(ctxRising, {
            type: 'bar',
            data: {
                labels: rising[0],
                datasets: [{
                    label: '% 증가량(Percent Increase)',
                    data: rising[1],
                    backgroundColor: background,
                    borderColor: border,
                    borderWidth: 1
                    }]
            },
            options: {
                title: {
                    display: true,
                    text: risingChartTitle
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
     }

    //checks if search term is in "English" (i.e. alphanumeric)
    function isEnglish(searchTerm)
    {
        var len = searchTerm.length;
        var letterCode;

        for (var i = 0; i < len; i++)
        {
            letterCode = searchTerm.charCodeAt(i);
            if (!(letterCode > 47 && letterCode < 58) &&
                !(letterCode > 64 && letterCode < 91) &&
                !(letterCode > 96 && letterCode < 123))
            {
                return false;
            }
        }
        return true;
    };


    //format date and set maximum for the two date fields
    function dateSetter()
    {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();

        if(dd < 10)
        {
            dd= '0' + dd;
        }
        if(mm < 10)
        {
            mm = '0' + mm;
        }
        today = yyyy + '-' + mm + '-' + dd;
        lastYear = yyyy-1 + '-' + mm + '-' + dd;

        document.getElementById("startDate").setAttribute("max", today);
        document.getElementById("startDate").setAttribute("value", lastYear);

        document.getElementById("endDate").setAttribute("max", today);
        document.getElementById("endDate").setAttribute("value", today);
    }

    //format parameter values that is required by google trends API.
    // params: javascript object containing name : value pairs.
    function formatParams(params)
    {
        return "?" + Object.keys(params).map(function(key)
        {
            return key+"="+encodeURIComponent(params[key])
        }).join("&")
    }
        //]]>