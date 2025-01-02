# Define the length of the secret
$secretLength = 64

# Generate a random byte array
$randomBytes = New-Object byte[] $secretLength
[System.Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($randomBytes)

# Convert the byte array to a Base64 string
$jwtSecret = [Convert]::ToBase64String($randomBytes)

# Output the secret
Write-Output $jwtSecret
