$(function () {


    var violet = '#DF99CA',
        red    = '#F0404C',
        green  = '#7CF29C';

    // ------------------------------------------------------- //
    // Charts Gradients
    // ------------------------------------------------------ //
    var ctx1 = $("canvas").get(0).getContext("2d");
    var gradient1 = ctx1.createLinearGradient(150, 0, 150, 300);
    gradient1.addColorStop(0, 'rgba(210, 114, 181, 0.91)');
    gradient1.addColorStop(1, 'rgba(177, 62, 162, 0)');

    var gradient2 = ctx1.createLinearGradient(10, 0, 150, 300);
    gradient2.addColorStop(0, 'rgba(252, 117, 176, 0.84)');
    gradient2.addColorStop(1, 'rgba(250, 199, 106, 0.92)');


    var BARCHARTEXMPLE    = $('#barChartExample');
    var obj = window.document.location;
    //请求渲染模拟
    $.ajax({
        url: "/list",
        type: "get",
        success: function f(data) {
            alert("success!");
            var barChartExample = new Chart(BARCHARTEXMPLE, {
                type: 'bar',
                options: {
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                color: '#fff'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            ticks: {
                                max: 100,
                                min: 0
                            },
                            gridLines: {
                                color: '#fff'
                            }
                        }]
                    },
                    legend: false
                },
                data: {
                    labels: data.name_list,
                    datasets: [
                        {
                            label: "Data Set 1",
                            backgroundColor: [
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2
                            ],
                            hoverBackgroundColor: [
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2
                            ],
                            borderColor: [
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2,
                                gradient2
                            ],
                            borderWidth: 1,
                            data: data.num_list
                        }
                    ]
                }
            });
        },
        error: function () {
            alert("failed!");
        }
    });



    // ------------------------------------------------------- //
    // Line Chart
    // ------------------------------------------------------ //

    var LINECHART = $('#lineCahrtsm1');
    var myLineChart = new Chart(LINECHART, {
        type: 'line',
        options: {
            scales: {
                xAxes: [{
                    display: false
                }],
                yAxes: [{
                    ticks: {
                        max: 50,
                        min: 0
                    },
                    display: false
                }]
            },
            legend: {
                display: false
            }
        },
        data: {
            labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"],
            datasets: [
                {
                    label: "Page Visitors",
                    fill: true,
                    lineTension: 0.4,
                    backgroundColor: "transparent",
                    borderColor: green,
                    pointBorderColor: green,
                    pointHoverBackgroundColor: green,
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 3,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 1,
                    pointRadius: 0,
                    pointHitRadius: 1,
                    data: [20, 14, 21, 15, 22, 8, 18, 13, 21, 13, 17, 13, 20, 15],
                    spanGaps: false
                }
            ]
        }
    });

    // ------------------------------------------------------- //
    // Doughnut Chart
    // ------------------------------------------------------ //
    var DOUGHNUTCHARTEXMPLE  = $('#doughnutChartExample');
    var pieChartExample = new Chart(DOUGHNUTCHARTEXMPLE, {
        type: 'doughnut',
        options: {
            cutoutPercentage: 80,
        },
        data: {
            labels: [
                "A",
                "B",
                "C",
                "D"
            ],
            datasets: [
                {
                    data: [250, 50, 100, 40],
                    borderWidth: 0,
                    backgroundColor: [
                        '#df99ca',
                        '#c374ab',
                        "#a44e8a",
                        "#81376a"
                    ],
                    hoverBackgroundColor: [
                        '#df99ca',
                        '#c374ab',
                        "#a44e8a",
                        "#81376a"
                    ]
                }]
            }
    });

    var pieChartExample = {
        responsive: true
    };



    // ------------------------------------------------------- //
    // Polar Chart
    // ------------------------------------------------------ //
    var POLARCHARTEXMPLE  = $('#polarChartExample');
    var polarChartExample = new Chart(POLARCHARTEXMPLE, {
        type: 'polarArea',
        options: {
            elements: {
                arc: {
                    borderWidth: 0,
                    borderColor: '#aaa'
                }
            }
        },
        data: {
            datasets: [{
                data: [
                    7,
                    16,
                    12,
                    11
                ],
                backgroundColor: [
                    green,
                    "#6adf8a",
                    "#50c670",
                    "#3fac5c"
                ],
                label: 'My dataset' // for legend
            }],
            labels: [
                "A",
                "B",
                "C",
                "D"
            ]
        }
    });

    var polarChartExample = {
        responsive: true
    };


    // ------------------------------------------------------- //
    // Pie Chart
    // ------------------------------------------------------ //
    var PIECHARTEXMPLE    = $('#pieChartExample');
    var pieChartExample = new Chart(PIECHARTEXMPLE, {
        type: 'pie',
        data: {
            labels: [
                "A",
                "B",
                "C",
                "D"
            ],
            datasets: [
                {
                    data: [300, 50, 100, 80],
                    borderWidth: 0,
                    backgroundColor: [
                        green,
                        "#6adf8a",
                        "#50c670",
                        "#3fac5c",
                        "#2a9346"
                    ],
                    hoverBackgroundColor: [
                        green,
                        "#6adf8a",
                        "#50c670",
                        "#3fac5c",
                        "#2a9346"
                    ]
                }]
            }
    });

    var pieChartExample = {
        responsive: true
    };



    // ------------------------------------------------------- //
    // Radar Chart
    // ------------------------------------------------------ //
    var RADARCHARTEXMPLE  = $('#radarChartExample');
    var radarChartExample = new Chart(RADARCHARTEXMPLE, {
        type: 'radar',
        data: {
            labels: ["A", "B", "C", "D", "E", "C"],
            datasets: [
                {
                    label: "First dataset",
                    backgroundColor: "rgba(252, 117, 176, 0.4)",
                    borderWidth: 2,
                    borderColor: "rgba(252, 117, 176, 0.84)",
                    pointBackgroundColor: "rgba(252, 117, 176, 0.84)",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "rgba(252, 117, 176, 0.84)",
                    data: [65, 59, 90, 81, 56, 55]
                },
                {
                    label: "Second dataset",
                    backgroundColor: "rgba(250, 199, 106, 0.4)",
                    borderWidth: 2,
                    borderColor: "rgba(250, 199, 106, 0.92)",
                    pointBackgroundColor: "rgba(250, 199, 106, 0.92)",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "rgba(250, 199, 106, 0.92)",
                    data: [50, 60, 80, 45, 96, 70]
                }
            ]
        }
    });
    var radarChartExample = {
        responsive: true
    };



});
