import { useEffect, useState } from "react";
import { getDashboard } from "../services/productService";
import { Link } from "react-router-dom";

function Dashboard() {

    const [dashboard, setDashboard] = useState(null);

    useEffect(() => {

        loadDashboard();

    }, []);

    const loadDashboard = async () => {

        try {

            const response = await getDashboard();

            setDashboard(response.data);

        } catch (error) {

            alert("Unable to load dashboard");

        }

    };

    if (!dashboard) {

        return <h2>Loading...</h2>;

    }

    return (

        <div style={{ padding: 30 }}>

            <h1>StockFlow Dashboard</h1>

            <Link to="/products">
                Manage Products
            </Link>

            <hr />

            <h3>Total Products : {dashboard.totalProducts}</h3>

            <h3>Total Quantity : {dashboard.totalQuantity}</h3>

            <hr />

            <h2>Low Stock Items</h2>

            <table border="1" cellPadding="10">

                <thead>

                <tr>

                    <th>Name</th>
                    <th>SKU</th>
                    <th>Quantity</th>

                </tr>

                </thead>

                <tbody>

                {dashboard.lowStockProducts.map((product) => (

                    <tr key={product.id}>

                        <td>{product.name}</td>

                        <td>{product.sku}</td>

                        <td>{product.quantityOnHand}</td>

                    </tr>

                ))}

                </tbody>

            </table>

        </div>

    );

}

export default Dashboard;