<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

<#--        <script type="text/javascript" src="/jquery/jquery-3.3.1.js"></script>-->
<#--        <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>-->
<#--        <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">-->

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <title>MarketX</title>
</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
    <div class="container">
        <a class="navbar-brand" href="/main">MarketX</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Content -->
<div class="container margin-top">
    <div class="row">
        <div class="col-lg-12">

            <form class="form-horizontal" action="/main" method="GET">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Available currencies</legend>

                    <!-- Select Basic -->

                    <div class="container">
                        <div class="row">
                            <div class="col-sm-4">
                                <h3>From</h3>
                                <div class="form-inline form-group">
                                    <select id="fromCurrency" name="fromCurrency" class="form-control">
                                        <#list currencyModel as value>
                                            <option value="${value.code}">${value.code}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <h3>To</h3>
                                <!-- Select Basic -->
                                <div class="form-inline form-group">
                                    <select id="toCurrency" name="toCurrency" class="form-control">
                                        <#list currencyModel as value>
                                            <option value="${value.code}">${value.code}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <h3>Exchange rate</h3>
                                <input type="text" id="rate" name="rate"
                                       value="<#if rateModel??>${rateModel}</#if>" disabled>
                            </div>
                        </div>
                    </div>

                    <!-- Button -->
                    <div class="form-inline form-group col-md-6">
                        <label class="control-label" for="searchButton"></label>
                        <div class="col-md-4">
                            <button id="searchButton" class="btn btn-success">Submit</button>
                        </div>
                    </div>

                </fieldset>
            </form>

        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div id="container" style="height: 500px; width: 100%"></div>

            <script type="text/javascript"
                    src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>

            <script type="text/javascript">
                var dom = document.getElementById("container");
                var myChart = echarts.init(dom);
                var app = {};
                option = null;
                app.title = '2015';

                var rawData = [<#if historicalModel??><#list historicalModel as key, value> ['${key}', '${value.open}', '${value.close}', ''
                    , '', '${value.low}', '${value.high}', '', '', '']<#sep>, </#sep></#list></#if>];

                function calculateMA(dayCount, data) {
                    var result = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        if (i < dayCount) {
                            result.push('-');
                            continue;
                        }
                        var sum = 0;
                        for (var j = 0; j < dayCount; j++) {
                            sum += data[i - j][1];
                        }
                        result.push(sum / dayCount);
                    }
                    return result;
                }

                var dates = rawData.map(function (item) {
                    return item[0];
                });

                var data = rawData.map(function (item) {
                    return [+item[1], +item[2], +item[5], +item[6]];
                });
                var option = {
                    backgroundColor: '#21202D',
                    legend: {
                        data: ['Candle stick', 'Average'],
                        inactiveColor: '#777',
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            animation: false,
                            type: 'cross',
                            lineStyle: {
                                color: '#376df4',
                                width: 2,
                                opacity: 1
                            }
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: dates,
                        axisLine: {lineStyle: {color: '#8392A5'}}
                    },
                    yAxis: {
                        scale: true,
                        axisLine: {lineStyle: {color: '#8392A5'}},
                        splitLine: {show: false}
                    },
                    grid: {
                        bottom: 80
                    },
                    dataZoom: [{
                        textStyle: {
                            color: '#8392A5'
                        },
                        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                        handleSize: '80%',
                        dataBackground: {
                            areaStyle: {
                                color: '#8392A5'
                            },
                            lineStyle: {
                                opacity: 0.8,
                                color: '#8392A5'
                            }
                        },
                        handleStyle: {
                            color: '#fff',
                            shadowBlur: 3,
                            shadowColor: 'rgba(0, 0, 0, 0.6)',
                            shadowOffsetX: 2,
                            shadowOffsetY: 2
                        }
                    }, {
                        type: 'inside'
                    }],
                    animation: false,

                    series: [
                        {
                            type: 'candlestick',
                            name: 'Candle stick',
                            data: data,
                            itemStyle: {
                                normal: {
                                    color: '#0CF49B',
                                    color0: '#FD1050',
                                    borderColor: '#0CF49B',
                                    borderColor0: '#FD1050'
                                }
                            }
                        },
                        {
                            name: 'Average',
                            type: 'line',
                            data: calculateMA(1, data),
                            smooth: true,
                            showSymbol: false,
                            lineStyle: {
                                normal: {
                                    width: 1.3
                                }
                            }
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            </script>
        </div>
    </div>
</div>
</body>
</html>