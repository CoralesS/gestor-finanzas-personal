<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Categoría</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        .hover-card {
            transition: transform 0.2s, box-shadow 0.2s;
            cursor: pointer;
            border-radius: 12px;
        }
        .hover-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
            border-color: #0d6efd !important;
        }
        .borde-punteado {
            border: 2px dashed #adb5bd;
            background-color: transparent;
        }
        .borde-punteado:hover {
            border-color: #198754 !important; /* Verde al pasar el ratón */
            background-color: rgba(25, 135, 84, 0.05);
        }
    </style>
</head>
<body class="bg-light">

    <!-- Navegación para regresar -->
    <nav class="navbar navbar-light bg-white shadow-sm mb-5">
        <div class="container">
            <a class="navbar-brand text-primary fw-bold" href="index.jsp">
                <i class="bi bi-arrow-left-circle me-2"></i>Volver al Inicio
            </a>
        </div>
    </nav>

    <div class="container">
        <!-- Título principal -->
        <div class="text-center mb-5">
            <h2 class="fw-bold">¿En qué categoría deseas registrar movimientos?</h2>
            <p class="text-muted">Selecciona una categoría de la lista para administrar sus gastos.</p>
        </div>

        <!-- Matriz de Tarjetas -->
        <div class="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-4 mb-5">

            <!-- Bucle que imprime todas las categorías existentes -->
            <c:forEach var="cat" items="${categorias}">
                <div class="col">

                    <a href="MovimientoController?accion=listar&idcategoria=${cat.id}&nombre=${cat.nombre}" class="text-decoration-none text-dark">
                        <div class="card h-100 shadow-sm text-center hover-card border-0">
                            <div class="card-body p-4">
                                <i class="bi bi-folder2-open fs-1 text-primary mb-3 d-block"></i>
                                <h5 class="card-title fw-bold">${cat.nombre}</h5>
                                <p class="card-text text-muted small text-truncate" title="${cat.descripcion}">
                                    ${cat.descripcion}
                                </p>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>

            <!-- Tarjeta Nueva Categoría -->
            <div class="col">
                <a href="CategoriaController?accion=nueva" class="text-decoration-none text-dark">
                    <div class="card h-100 shadow-none text-center hover-card borde-punteado">
                        <div class="card-body p-4 d-flex flex-column justify-content-center">
                            <i class="bi bi-plus-circle-dotted fs-1 text-success mb-2"></i>
                            <h5 class="card-title text-success fw-bold mt-2">Crear Categoría</h5>
                        </div>
                    </div>
                </a>
            </div>

        </div>
    </div>

</body>
</html>