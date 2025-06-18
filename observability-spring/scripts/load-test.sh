#!/bin/bash

# Simple load testing script for the observability-spring application
# This script uses curl to send HTTP requests to the API endpoints.

BASE_URL="http://localhost:8080/api"
PRODUCT_ENDPOINT="\${BASE_URL}/products"
ORDER_ENDPOINT="\${BASE_URL}/orders"

# Number of iterations for the main loop
ITERATIONS=20
# Number of product creation/modification operations per iteration
PRODUCT_OPS=3
# Number of order creation operations per iteration
ORDER_OPS=2

echo "Starting load test against \${BASE_URL}..."
echo "Running for \${ITERATIONS} iterations."

# Function to generate a random string for product names
random_string() {
    cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 8 | head -n 1
}

# Function to generate a random price (integer part.decimal part)
random_price() {
    echo "\$(( ( RANDOM % 100 ) + 1 )).\$(( RANDOM % 99 ))"
}

# Function to generate a random quantity for products
random_quantity() {
    echo $(( (RANDOM % 50) + 1 ))
}

# Store IDs of created products
declare -a product_ids

# Create some initial products to work with
echo "Creating initial products..."
for i in $(seq 1 5); do
    name="InitialProduct_\$(random_string)"
    price=\$(random_price)
    quantity=\$(random_quantity)

    # Send request and capture response
    response=\$(curl -s -w "\n%{http_code}" -X POST "\${PRODUCT_ENDPOINT}" \
        -H "Content-Type: application/json" \
        -d "{\"name\":\"\${name}\", \"price\":\${price}, \"quantity\":\${quantity}}")

    # Extract body and HTTP code
    http_code=\$(echo "\${response}" | tail -n1)
    body=\$(echo "\${response}" | sed '\$d')

    if [[ "\${http_code}" -eq 201 ]]; then
        id=\$(echo "\${body}" | jq -r '.id')
        if [[ ! -z "\${id}" && "\${id}" != "null" ]]; then
            product_ids+=(\${id})
            echo "Created initial product: ID=\${id}, Name=\${name}, HTTP Code: \${http_code}"
        else
            echo "Failed to parse ID from product creation. Body: \${body}, HTTP Code: \${http_code}"
        fi
    else
        echo "Failed to create initial product. HTTP Code: \${http_code}, Body: \${body}"
    fi
    sleep 0.2 # Small delay
done

echo "Initial product IDs: \${product_ids[@]}"
if [ \${#product_ids[@]} -eq 0 ]; then
    echo "Warning: No initial products were successfully created. Order tests might fail or not run."
fi

# Main loop for generating load
for i in \$(seq 1 \${ITERATIONS}); do
    echo ""
    echo "--- Iteration \$i of \${ITERATIONS} ---"

    # === Product Operations ===
    echo "Performing Product operations..."
    for j in \$(seq 1 \${PRODUCT_OPS}); do
        prod_name="Product_\$(random_string)_\$i-\$j"
        prod_price=\$(random_price)
        prod_quantity=\$(random_quantity)
        echo "Attempting to create product: \${prod_name}"

        create_response_full=\$(curl -s -w "\n%{http_code}" -X POST "\${PRODUCT_ENDPOINT}" \
            -H "Content-Type: application/json" \
            -d "{\"name\":\"\${prod_name}\", \"price\":\${prod_price}, \"quantity\":\${prod_quantity}}")

        create_http_code=\$(echo "\${create_response_full}" | tail -n1)
        create_body=\$(echo "\${create_response_full}" | sed '\$d')

        if [[ "\${create_http_code}" -eq 201 ]]; then
            created_id=\$(echo "\${create_body}" | jq -r '.id')
            if [[ ! -z "\${created_id}" && "\${created_id}" != "null" ]]; then
                product_ids+=(\${created_id})
                echo "  Created Product ID: \${created_id}, HTTP Code: \${create_http_code}"

                echo "  Attempting to GET product ID: \${created_id}"
                curl -s -X GET "\${PRODUCT_ENDPOINT}/\${created_id}" -o /dev/null

                updated_name="Updated_\${prod_name}"
                updated_price=\$(random_price)
                echo "  Attempting to UPDATE product ID: \${created_id}"
                curl -s -X PUT "\${PRODUCT_ENDPOINT}/\${created_id}" \
                    -H "Content-Type: application/json" \
                    -d "{\"name\":\"\${updated_name}\", \"price\":\${updated_price}, \"quantity\":\${prod_quantity}}" -o /dev/null
            else
                echo "  Failed to parse ID from created product. Body: \${create_body}, HTTP Code: \${create_http_code}"
            fi
        else
             echo "  Failed to create product in iteration \$i-\$j. HTTP Code: \${create_http_code}, Body: \${create_body}"
        fi
        sleep 0.1
    done

    echo "Attempting to GET all products..."
    curl -s -X GET "\${PRODUCT_ENDPOINT}" -o /dev/null
    sleep 0.1

    # === Order Operations ===
    if [ \${#product_ids[@]} -gt 0 ]; then
        echo "Performing Order operations..."
        for k in \$(seq 1 \${ORDER_OPS}); do
            rand_idx=\$(( RANDOM % \${#product_ids[@]} ))
            target_product_id=\${product_ids[\$rand_idx]}
            order_quantity=\$(( ( RANDOM % 3 ) + 1 )) # Order small quantities to increase chance of success

            if [ -z "\${target_product_id}" ] || [ "\${target_product_id}" == "null" ]; then
                echo "  Skipping order, target_product_id is invalid (\${target_product_id})."
                continue
            fi

            echo "  Attempting to place order for Product ID: \${target_product_id}, Quantity: \${order_quantity}"
            order_response_full=\$(curl -s -w "\n%{http_code}" -X POST "\${ORDER_ENDPOINT}" \
                -H "Content-Type: application/json" \
                -d "{\"productId\":\${target_product_id}, \"quantity\":\${order_quantity}}")

            order_http_code=\$(echo "\${order_response_full}" | tail -n1)
            order_body=\$(echo "\${order_response_full}" | sed '\$d')

            if [[ "\${order_http_code}" -eq 200 ]]; then
                echo "    Order successful for product \${target_product_id}. HTTP Code: \${order_http_code}. Response: \$(echo \${order_body} | jq -c .)"
            else
                echo "    Order failed for product \${target_product_id}. HTTP Code: \${order_http_code}. Response: \$(echo \${order_body} | jq -c .)"
            fi
            sleep 0.1
        done
    else
        echo "Skipping order operations as no product IDs are available."
    fi

    # Randomly delete one of the earlier products (if available and more than a few exist)
    # Ensure product_ids array is not empty before trying to access/delete
    if [ \${#product_ids[@]} -gt 3 ]; then
        delete_idx=0
        delete_id=\${product_ids[\$delete_idx]}

        # Check if delete_id is valid before attempting delete
        if [[ ! -z "\${delete_id}" && "\${delete_id}" != "null" ]]; then
            echo "Attempting to DELETE product ID: \${delete_id}"
            curl -s -X DELETE "\${PRODUCT_ENDPOINT}/\${delete_id}" -o /dev/null

            # Remove from array (bash specific: create new array without the element)
            temp_ids=()
            for (( idx=0; idx<\${#product_ids[@]}; idx++ )); do
                if [[ \${idx} -ne \${delete_idx} ]]; then
                    temp_ids+=("\${product_ids[\$idx]}")
                fi
            done
            product_ids=("${temp_ids[@]}")
            unset temp_ids # Clean up temp array
            echo "  Remaining product IDs after deletion: \${product_ids[@]}"
        else
            echo "  Skipping deletion, delete_id was invalid."
        fi
        sleep 0.1
    fi
done

echo ""
echo "Load test finished."
echo "Final list of product IDs (some might have been deleted): \${product_ids[@]}"
