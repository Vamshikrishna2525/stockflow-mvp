import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  getProducts,
  addProduct,
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

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      const response = await getProducts();
      setProducts(response.data);
    } catch (e) {
      alert("Unable to load products");
    }
  };

  const handleChange = (e) => {
    setProduct({
      ...product,
      [e.target.name]: e.target.value
    });
  };

  const saveProduct = async (e) => {
    e.preventDefault();

    try {
      await addProduct(product);

      alert("Product Added");

      setProduct(emptyProduct);

      loadProducts();

    } catch (e) {

      alert("Unable to save product");

    }
  };

  const removeProduct = async (id) => {

    if (!window.confirm("Delete Product?"))
      return;

    try {

      await deleteProduct(id);

      loadProducts();

    } catch (e) {

      alert("Delete Failed");

    }

  };

  return (

    <div style={{padding:30}}>

      <Link to="/dashboard">
        Dashboard
      </Link>

      <h1>Products</h1>

      <form onSubmit={saveProduct}>

        <input
          name="name"
          placeholder="Name"
          value={product.name}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          name="sku"
          placeholder="SKU"
          value={product.sku}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          name="description"
          placeholder="Description"
          value={product.description}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          type="number"
          name="quantityOnHand"
          placeholder="Quantity"
          value={product.quantityOnHand}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          type="number"
          step="0.01"
          name="costPrice"
          placeholder="Cost Price"
          value={product.costPrice}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          type="number"
          step="0.01"
          name="sellingPrice"
          placeholder="Selling Price"
          value={product.sellingPrice}
          onChange={handleChange}
        />

        <br/><br/>

        <input
          type="number"
          name="lowStockThreshold"
          placeholder="Low Stock Threshold"
          value={product.lowStockThreshold}
          onChange={handleChange}
        />

        <br/><br/>

        <button type="submit">
          Add Product
        </button>

      </form>

      <hr/>

      <table border="1" cellPadding="8">

        <thead>

        <tr>

          <th>Name</th>
          <th>SKU</th>
          <th>Qty</th>
          <th>Selling Price</th>
          <th>Action</th>

        </tr>

        </thead>

        <tbody>

        {products.map((p)=>(
          <tr key={p.id}>

            <td>{p.name}</td>
            <td>{p.sku}</td>
            <td>{p.quantityOnHand}</td>
            <td>{p.sellingPrice}</td>

            <td>

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