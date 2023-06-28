$(document).ready(function() {
    $(".form-control").on("keyup", function() {
        var namaPengaju = $("input[placeholder='Search by Nama Pengaju ...']").val().toLowerCase();
        var bentukRequest = $("input[placeholder='Search by Bentuk Request ...']").val().toLowerCase();
        var judulRequest = $("input[placeholder='Search by Judul Request ...']").val().toLowerCase();
        $.ajax({
            url: "/api/requests/staff/search",
            data: {
                namaPengaju: namaPengaju,
                bentukRequest: bentukRequest,
                judulRequest: judulRequest
            },
            success: function(response) {
                // Clear the existing table
                $(".datatable tbody").empty();

                if (response.length > 0) {
                    // Add new rows to the table
                    response.forEach(function(request) {
                        var statusClass = request.status === 'Ditolak' ? 'badge bg-danger' : (request.status === 'Disetujui' ? 'badge bg-success' : 'badge bg-warning');

                        var actions = '';
                        if (request.status === 'Pending') {
                            actions = '<div class="actions">' +
                                '<a class="btn btn-sm bg-danger-light" href="/staff/requests/view-request/' + request.id + '">' +
                                '<i class="feather-eye"></i>' +
                                '</a>' +
                                '</div>';
                        } else if (request.status === 'Disetujui') {
                            actions = '<div class="actions">' +
                                '<span>' +
                                '<a class="btn btn-sm bg-success rounded-circle">' +
                                '<i class="feather-check" style="color: white;"></i>' +
                                '</a>' +
                                '</span>' +
                                '</div>';
                        } else if (request.status === 'Ditolak') {
                            actions = '<div class="actions">' +
                                '<span>' +
                                '<a class="btn btn-sm bg-danger rounded-circle">' +
                                '<i class="feather-x" style="color: white;"></i>' +
                                '</a>' +
                                '</span>' +
                                '</div>';
                        }

                        var row = '<tr>' +
                            '<td class="text-center">' + request.namaPengaju + '</td>' +
                            '<td class="text-center">' + request.bentukRequest + '</td>' +
                            '<td class="text-center">' + request.judulRequest + '</td>' +
                            '<td class="text-center">' + request.deadline + '</td>' +
                            '<td class="text-center"><span class="' + statusClass + '">' + request.status + '</span></td>' +
                            '<td class="text-end">' + actions + '</td>' +
                            '</tr>';
                        $(".datatable tbody").append(row);
                    });
                } else {
                    // Display 'No Result Found'
                    var row = '<tr>' +
                        '<td colspan="6" class="text-center">No Result Found</td>' +
                        '</tr>';
                    $(".datatable tbody").append(row);
                }
            },
            error: function() {
                // Handle error case
                var row = '<tr>' +
                    '<td colspan="6" class="text-center">Error fetching data</td>' +
                    '</tr>';
                $(".datatable tbody").append(row);
            }
        });
    });
});
