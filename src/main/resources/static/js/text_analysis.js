//<![CDATA[
        // get Text analysis data from DB
        function getTextAnalysis()
        {
            var params = {
                gameName : '"' + document.getElementById("searchParam").elements[0].value + '"',
                startDate : document.getElementById("searchParam").elements[1].value,
                endDate : document.getElementById("searchParam").elements[2].value
            }
            var url = "get_text_analysis" + formatParams(params);
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("container").innerHTML = '&nbsp;';
                    document.getElementById("container").innerHTML = '<canvas id = "common_nouns"></canvas>';
                    formatDataForChart(this.responseText);
                }
            };
            xhttp.open("GET", url, true);
            xhttp.send();
            return false;
        }

        function formatDataForChart(data)
        {
            var jsonData = JSON.parse(data);
            var chartData = [];
            var noun = [];
            var count = [];
            for (var key in jsonData)
            {
                if (jsonData.hasOwnProperty(key))
                {
                    noun.push(key);
                    count.push(jsonData[key]);
                }
            }
            chartData.push(noun);
            chartData.push(count);

            drawChart(chartData);
        }

        function drawChart(data)
        {
            var title = "가장 많이 언급된 명사 (Nouns that were most commonly mentioned)."

            var ctx = document.getElementById("common_nouns").getContext('2d');
            ctx.canvas.width = window.innerWidth;
            ctx.canvas.height = window.innerHeight - 200;
            var background = [];
            var border = [];
            var num_data_point = data[1].length;
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
            var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data[0],
                datasets: [{
                    label: '횟수(Count)',
                    data: data[1],
                    backgroundColor: background,
                    borderColor: border,
                    borderWidth: 1
                    }]
            },
            options: {
                title: {
                    display: true,
                    text: title
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
            document.getElementById("startDate").setAttribute("max", today);
            document.getElementById("endDate").setAttribute("max", today);
            document.getElementById("endDate").setAttribute("value", today);
        }

        //format parameter values that is required by the script calling DB.
        // params: javascript object containing name : value pairs.
        function formatParams(params)
        {
            return "?" + Object.keys(params).map(function(key)
            {
                return key+"="+encodeURIComponent(params[key])
            }).join("&")
        }
       //]]>