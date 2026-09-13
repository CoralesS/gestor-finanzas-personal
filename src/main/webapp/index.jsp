<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestor de Finanzas | Inicio</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .hero-section {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 80px 0 60px;
            border-radius: 0 0 30px 30px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .feature-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border-radius: 15px;
        }
        .feature-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.15) !important;
        }
        .icon-wrapper {
            width: 80px;
            height: 80px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
        }
    </style>
</head>
<body class="bg-light">

    <!-- Barra de Navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">
                <i class="bi bi-wallet2 me-2 text-primary"></i>GestorFinanzas
            </a>
        </div>
    </nav>

    <!-- Encabezado Principal -->
    <header class="hero-section text-center mb-5">
        <div class="container">
            <h1 class="display-4 fw-bold mb-3">Toma el control de tu dinero</h1>
            <p class="lead mb-4 fw-light">Administra tus categorías y registra tus gastos diarios de forma rápida y segura</p>
        </div>
    </header>

    <!-- Panel de Control -->
    <div class="container">
        <div class="row justify-content-center g-4">

            <!-- Tarjeta 1: Categorías -->
            <div class="col-md-5">
                <div class="card h-100 border-0 shadow-sm feature-card">
                    <div class="card-body text-center p-5">
                        <div class="bg-primary bg-opacity-10 text-primary icon-wrapper mb-4">
                            <i class="bi bi-tags-fill fs-1"></i>
                        </div>
                        <h3 class="card-title fw-bold">Gestión de Categorías</h3>
                        <p class="card-text text-muted mb-4">Crea, edita y organiza los rubros de tus finanzas. Define la estructura antes de registrar tus gastos.</p>
                        <a href="CategoriaController?accion=listar" class="btn btn-outline-primary w-100 fw-bold">
                            <i class="bi bi-folder2-open me-2"></i>Gestionar Categorías
                        </a>
                    </div>
                </div>
            </div>

            <!-- Tarjeta 2: Resumen / Movimientos -->
            <div class="col-md-5">
                <div class="card h-100 border-0 shadow-sm feature-card">
                    <div class="card-body text-center p-5">
                        <div class="bg-success bg-opacity-10 text-success icon-wrapper mb-4">
                            <i class="bi bi-cash-coin fs-1"></i>
                        </div>
                        <h3 class="card-title fw-bold">Control de Gastos</h3>
                        <p class="card-text text-muted mb-4">Añade nuevos movimientos, consulta tu historial y mantén un registro detallado en cada categoría.</p>
                        <!-- Ambos botones apuntan a categorías porque desde ahí navegas a los movimientos en tu lógica actual -->
                        <a href="CategoriaController?accion=seleccionar" class="btn btn-outline-success w-100 fw-bold">
                            <i class="bi bi-calculator me-2"></i>Registrar Movimientos
                        </a>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- Pie de página -->
    <footer class="text-center text-muted mt-5 pb-4">
        <small>&copy; 2026 Gestor de Finanzas Web. Arquitectura Java Servlets y MySQL.</small>
    </footer>

    <!-- Scripts de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>