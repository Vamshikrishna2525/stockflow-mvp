import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
    getProducts,
    addProduct,
    updateProduct,
    deleteProduct
} from "../services/productService";

function Products() {

    const emptyProduct = {
        name: "",
        sku: "",
        description: "",
        quantityOnHand: 0,
        costPrice: 0,
        sellingPrice: 0,
        lowStockThreshold: 5
    };

    const [products, setProducts] = useState([]);
    const [product, setProduct] = useState(emptyProduct);
    const [editingId, setEditingId] = useState(null);

    useEffect(() => {
        loadProducts();
    }, []);

    const loadProducts = async () => {

        try {

            const response = await getProducts();

            setProducts(response.data);

        } catch (error) {

            alert("Unable to load products");

        }

    };

    const handleChange = (e) => {

        setProduct({
            ...product,
            [e.target.name]:
                e.target.type === "number"
                    ? Number(e.target.value)
                    : e.target.value
        });

    };

    const saveProduct = async (e) => {

        e.preventDefault();

        try {

            if (editingId) {

                await updateProduct(editingId, product);

                alert("Product Updated Successfully");

            } else {

                await addProduct(product);

                alert("Product Added Successfully");

            }

            setProduct(emptyProduct);

            setEditingId(null);

            loadProducts();

        } catch (error) {

            alert("Unable to save product");

        }

    };

    const editProduct = (selectedProduct) => {

        setProduct({

            name: selectedProduct.name,
            sku: selectedProduct.sku,
            description: selectedProduct.description,
            quantityOnHand: selectedProduct.quantityOnHand,
            costPrice: selectedProduct.costPrice,
            sellingPrice: selectedProduct.sellingPrice,
            lowStockThreshold: selectedProduct.lowStockThreshold

        });

        setEditingId(selectedProduct.id);

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    };

    const cancelEdit = () => {

        setProduct(emptyProduct);

        setEditingId(null);

    };

    const removeProduct = async (id) => {

        if (!window.confirm("Are you sure you want to delete this product?"))
            return;

        try {

            await deleteProduct(id);

            alert("Product Deleted Successfully");

            loadProducts();

        } catch (error) {

            alert("Delete Failed");

        }

    };
        return (

        <div style={{ padding: 30 }}>

            <Link to="/dashboard">
                ← Dashboard
            </Link>

            <h1>Products</h1>

            <form onSubmit={saveProduct}>

                <label><b>Product Name</b></label><br />
                <input
                    name="name"
                    placeholder="Enter Product Name"
                    value={product.name}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label><b>SKU</b></label><br />
                <input
                    name="sku"
                    placeholder="Enter SKU"
                    value={product.sku}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label><b>Description</b></label><br />
                <input
                    name="description"
                    placeholder="Enter Description"
                    value={product.description}
                    onChange={handleChange}
                />

                <br /><br />

                <label><b>Quantity</b></label><br />
                <input
                    type="number"
                    name="quantityOnHand"
                    placeholder="Enter Quantity"
                    value={product.quantityOnHand}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label><b>Cost Price</b></label><br />
                <input
                    type="number"
                    step="0.01"
                    name="costPrice"
                    placeholder="Enter Cost Price"
                    value={product.costPrice}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label><b>Selling Price</b></label><br />
                <input
                    type="number"
                    step="0.01"
                    name="sellingPrice"
                    placeholder="Enter Selling Price"
                    value={product.sellingPrice}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label><b>Low Stock Threshold</b></label><br />
                <input
                    type="number"
                    name="lowStockThreshold"
                    placeholder="Enter Low Stock Threshold"
                    value={product.lowStockThreshold}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <button type="submit">
                    {editingId ? "Update Product" : "Add Product"}
                </button>

                {editingId && (
                    <>
                        {" "}
                        <button
                            type="button"
                            onClick={cancelEdit}
                        >
                            Cancel
                        </button>
                    </>
                )}

            </form>

            <hr />

            <table border="1" cellPadding="8">

                <thead>

                    <tr>

                        <th>Name</th>
                        <th>SKU</th>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Cost Price</th>
                        <th>Selling Price</th>
                        <th>Low Stock</th>
                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                    {products.map((p) => (

                        <tr key={p.id}>

                            <td>{p.name}</td>
                            <td>{p.sku}</td>
                            <td>{p.description}</td>
                            <td>{p.quantityOnHand}</td>
                            <td>{p.costPrice}</td>
                            <td>{p.sellingPrice}</td>
                            <td>{p.lowStockThreshold}</td>

                            <td>

                                <button
                                    onClick={() => editProduct(p)}
                                >
                                    Edit
                                </button>

                                {" "}

                                <button
                                    onClick={() => removeProduct(p.id)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

}

export default Products;