async function exportTable(userId) {
    // Fetch data from back-end
    const response = await fetch('/api/requests');
    const requestData = await response.json();

    // Prepare headers
    const wsData = [
        ['Nama Pengaju', 'Asal Pengaju', 'Nomor Handphone', 'Judul Request', 'Bentuk Request', 'Deadline', 'Status', 'Created At']
    ];

    // Add data to worksheet data
    requestData.forEach(request => {
        if(request.user_id === userId) { // Assuming the user id is in the 'user_id' field
            wsData.push([
                request.namaPengaju,
                request.asalPengaju,
                request.nomorHandphone,
                request.judulRequest,
                request.bentukRequest,
                request.deadline,
                request.status,
                request.createdAt
            ]);
        }
    });

    // Create a workbook and worksheet
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.aoa_to_sheet(wsData);
    XLSX.utils.book_append_sheet(wb, ws, 'Requests');

    // Export the workbook as an Excel file
    const filename = 'requests.xlsx';
    XLSX.writeFile(wb, filename);
}
