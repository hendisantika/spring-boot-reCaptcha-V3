#!/bin/bash

echo "Testing Employee Data Addition..."
echo "=================================="

# Test data
NAME="John Doe"
SALARY="75000"
ADDRESS="123 Main Street, Jakarta"
RECAPTCHA_TOKEN="test-token-for-simulation"

echo "Employee Data:"
echo "Name: $NAME"
echo "Salary: $SALARY"
echo "Address: $ADDRESS"
echo ""

# Submit form data
echo "Submitting employee data..."
RESPONSE=$(curl -s -w "HTTPSTATUS:%{http_code}" \
  -X POST \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=$NAME&salary=$SALARY&address=$ADDRESS&recaptcha-token=$RECAPTCHA_TOKEN" \
  http://localhost:8081/save)

HTTP_STATUS=$(echo $RESPONSE | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
BODY=$(echo $RESPONSE | sed -e 's/HTTPSTATUS:.*//g')

echo "HTTP Status: $HTTP_STATUS"

if [ $HTTP_STATUS -eq 200 ]; then
    echo "✅ Form submission successful!"
    echo ""
    echo "Checking if data was saved to database..."
    
    # Check database
    RECORD_COUNT=$(docker exec spring-boot-recaptcha-v3-mysql-1 mysql -u yu71 -p53cret recaptcha_db -e "SELECT COUNT(*) as count FROM employee;" 2>/dev/null | tail -1)
    
    if [ "$RECORD_COUNT" = "count" ]; then
        RECORD_COUNT="0"
    fi
    
    echo "Records in database: $RECORD_COUNT"
    
    if [ "$RECORD_COUNT" -gt 0 ]; then
        echo "✅ Employee data successfully saved to database!"
        echo ""
        echo "Employee records:"
        docker exec spring-boot-recaptcha-v3-mysql-1 mysql -u yu71 -p53cret recaptcha_db -e "SELECT id, name, salary, address FROM employee;" 2>/dev/null
    else
        echo "❌ No employee data found in database"
    fi
else
    echo "❌ Form submission failed with HTTP status: $HTTP_STATUS"
    echo "Response body: $BODY"
fi