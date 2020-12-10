<html>
<body>
	<h2>Hello World!</h2>

	<form method="POST"
		action="/SpringRestKyc/api/rest/kyc/upload"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>Select a file to upload</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
