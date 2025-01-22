# Wait for LocalStack to be ready
sleep 5

# Set environment variables for credentials
export AWS_ACCESS_KEY_ID=member
export AWS_SECRET_ACCESS_KEY=welcome

# Create S3 bucket with explicit credentials and SSL verification turned off
aws --endpoint-url=http://localhost:4566 s3 mb s3://member --no-verify-ssl --region us-east-1