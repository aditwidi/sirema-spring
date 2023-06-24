// Retrieve the values from the HTML
const totalAllRequest = parseInt(document.getElementById('totalAllRequest').innerText);
const totalAllPendingRequest = parseInt(document.getElementById('totalAllPendingRequest').innerText);
const totalAllDitolakRequest = parseInt(document.getElementById('totalAllDitolakRequest').innerText);
const totalAllDisetujuiRequest = parseInt(document.getElementById('totalAllDisetujuiRequest').innerText);

// Configuration options for the donut chart
const chartOptions = {
    series: [totalAllPendingRequest, totalAllDisetujuiRequest, totalAllDitolakRequest],
    labels: ['Pending', 'Disetujui', 'Ditolak'],
    chart: {
        type: 'donut',
    },
    colors: ['#FEB019', '#00E396', '#FF4560'], // Customize colors as needed
    responsive: [
        {
            breakpoint: 480,
            options: {
                chart: {
                    width: 300,
                },
                legend: {
                    position: 'bottom',
                },
            },
        },
    ],
};

// Initialize the donut chart with the options and data
const donutChart = new ApexCharts(document.querySelector('#donut-chart'), chartOptions);

// Render the chart
donutChart.render();
