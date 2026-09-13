<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Categorías</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        .table-wrapper { border-radius: 12px; overflow: hidden; }
        .btn-accion { transition: all 0.2s ease; }
        .btn-accion:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
    </style>
</head>
<body class="bg-light">

    <!-- Barra de Navegación -->
    <nav class="navbar navbar-light bg-white shadow-sm mb-5">
        <div class="container-fluid px-4">
            <a class="navbar-brand text-primary fw-bold" href="index.jsp">
                <i class="bi bi-arrow-left-circle me-2"></i>Volver al Inicio
            </a>
            <span class="navbar-text fw-bold text-secondary">
                <i class="bi bi-gear-fill me-1"></i> Administración
            </span>
        </div>
    </nav>

    <div class="container-fluid px-4 px-lg-5 mb-5">

        <!-- Encabezado de la página -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-dark mb-0"><i class="bi bi-tags text-primary me-2"></i>Mis Categorías</h2>
                <p class="text-muted mb-0">Crea, edita y organiza la estructura de tus finanzas.</p>
            </div>
        </div>

        <!-- Panel de Alertas -->
        <c:if test="${not empty error and param.error != 'categoria_en_uso'}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm border-0" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <c:if test="${not empty param.mensaje}">
            <div class="alert alert-success alert-dismissible fade show shadow-sm border-0" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>Acción completada: <strong>${param.mensaje}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <c:if test="${param.error == 'categoria_en_uso'}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm border-0 d-flex align-items-center" role="alert">
                <i class="bi bi-shield-lock-fill fs-4 me-3 text-danger"></i>
                <div>
                    <strong>¡Acción bloqueada por seguridad!</strong><br>
                    No puedes eliminar esta categoría porque contiene movimientos registrados. Elimina los gastos asociados primero.
                </div>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="row g-4">
            <!-- Columna Izquierda (Formulario) -->
            <div class="col-lg-4">
                <div class="card shadow-sm border-0 h-100">
                    <div class="card-header bg-white border-bottom-0 pt-4 pb-0">
                        <h5 class="fw-bold text-primary"><i class="bi bi-plus-circle me-2"></i>Registro Rápido</h5>
                    </div>
                    <div class="card-body p-4">
                        <form action="CategoriaController" method="POST">
                            <input type="hidden" name="accion" value="crear">

                            <div class="mb-3">
                                <label class="form-label fw-bold text-secondary small">NOMBRE</label>
                                <input type="text" name="nombre" class="form-control bg-light border-0" placeholder="Ej. Suscripciones" required>
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-bold text-secondary small">DESCRIPCIÓN</label>
                                <textarea name="descripcion" class="form-control bg-light border-0" rows="3" placeholder="Detalles..." required></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 fw-bold shadow-sm">
                                <i class="bi bi-save me-2"></i>Guardar Categoría
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Columna Derecha (Tabla de Datos) -->
            <div class="col-lg-8">
                <div class="card shadow-sm border-0 table-wrapper h-100">
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle m-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th class="ps-4 py-3" width="30%">Nombre</th>
                                        <th class="py-3" width="45%">Descripción</th>
                                        <th class="text-center py-3" width="25%">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="cat" items="${categorias}">
                                        <tr>
                                            <td class="ps-4 fw-bold text-dark">
                                                        <i class="bi bi-folder2 text-warning me-2"></i>${cat.nombre}
                                            </td>
                                            <td class="text-muted small">${cat.descripcion}</td>
                                            <td class="text-center">
                                                <!-- Botones con iconos -->
                                                <div class="btn-group shadow-sm" role="group">
                                                    <a href="MovimientoController?accion=listar&idcategoria=${cat.id}&nombre=${cat.nombre}" class="btn btn-sm btn-outline-success btn-accion" title="Ver Gastos">
                                                        <i class="bi bi-cash-stack"></i>
                                                    </a>
                                                    <a href="CategoriaController?accion=editar&id=${cat.id}" class="btn btn-sm btn-outline-primary btn-accion" title="Editar">
                                                        <i class="bi bi-pencil-square"></i>
                                                    </a>
                                                    <a href="CategoriaController?accion=eliminar&id=${cat.id}" class="btn btn-sm btn-outline-danger btn-accion" onclick="return confirm('¿Seguro que deseas eliminar la categoría: ${cat.nombre}?');" title="Eliminar">
                                                        <i class="bi bi-trash3-fill"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <!-- Mensaje si la tabla está vacía -->
                                    <c:if test="${empty categorias}">
                                        <tr>
                                            <td colspan="4" class="text-center py-5 text-muted">
                                                <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                                                Aún no tienes categorías registradas.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- Script de Bootstrap para hacer funcionar el botón de cerrar en las alertas -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>