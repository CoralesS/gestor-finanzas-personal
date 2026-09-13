<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Movimientos | ${param.nombre}</title>
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
            <!-- Redirige a la matriz de categorías -->
            <a class="navbar-brand text-primary fw-bold" href="CategoriaController?accion=seleccionar">
                <i class="bi bi-arrow-left-circle me-2"></i>Volver a Categorías
            </a>
            <span class="navbar-text fw-bold text-secondary">
                <i class="bi bi-wallet2 me-1"></i> Control de Gastos
            </span>
        </div>
    </nav>

<div class="container-fluid px-4 px-lg-5 mb-5">

    <!-- Cabecera -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="fw-bold text-dark mb-0">
                <i class="bi bi-folder2-open text-warning me-2"></i>${param.nombre}
            </h2>
            <p class="text-muted mb-0">Registra y administra los movimientos de esta categoría.</p>
        </div>
    </div>

    <!-- Alertas-->
    <c:if test="${param.mensaje == 'exito'}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm border-0" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>Gasto registrado correctamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.mensaje == 'eliminado'}">
        <div class="alert alert-warning alert-dismissible fade show shadow-sm border-0" role="alert">
            <i class="bi bi-trash3-fill me-2"></i>Gasto eliminado del sistema.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm border-0" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <!-- Columna Izquierda (Formulario) -->
        <div class="col-lg-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-white border-bottom-0 pt-4 pb-0">
                    <h5 class="fw-bold text-primary"><i class="bi bi-plus-circle me-2"></i>Nuevo Gasto</h5>
                </div>
                <div class="card-body p-4">
                    <form action="MovimientoController?accion=crear" method="post">

                        <!-- Campos ocultos de enrutamiento -->
                        <input type="hidden" name="idcategoria" value="${param.idcategoria}">
                        <input type="hidden" name="nombre" value="${param.nombre}">

                        <div class="mb-3">
                            <label class="form-label fw-bold text-secondary small">CONCEPTO</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-0"><i class="bi bi-card-text"></i></span>
                                <input type="text" name="concepto" class="form-control bg-light border-0" required placeholder="Ej: Cena con amigos">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold text-secondary small">MONTO</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-0 fw-bold text-danger">S/</span>
                                <input type="number" step="0.01" name="monto" class="form-control bg-light border-0" required placeholder="0.00">
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary small">FECHA</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-0"><i class="bi bi-calendar-date"></i></span>
                                <input type="date" name="fecha" class="form-control bg-light border-0" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100 fw-bold shadow-sm">
                            <i class="bi bi-save me-2"></i>Guardar Gasto
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Columna Derecha (Tabla de Resultados) -->
        <div class="col-lg-8">
            <div class="card shadow-sm border-0 table-wrapper h-100">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle m-0">
                            <thead class="table-dark">
                                <tr>
                                    <th class="ps-4 py-3" width="40%">Concepto</th>
                                    <th class="py-3" width="20%">Monto</th>
                                    <th class="py-3" width="25%">Fecha</th>
                                    <th class="text-center py-3" width="15%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="mov" items="${movimientos}">
                                    <tr>
                                        <td class="ps-4 fw-bold text-dark">${mov.concepto}</td>
                                        <td>
                                            <span class="badge bg-danger bg-opacity-10 text-danger fs-6 px-3 py-2 border border-danger border-opacity-25 rounded-pill">
                                                S/ ${mov.monto}
                                            </span>
                                        </td>
                                        <td class="text-muted"><i class="bi bi-calendar3 me-2"></i>${mov.fecha}</td>
                                            <td class="text-center">
                                                <!-- Agrupamos los botones para que se vean unidos y limpios -->
                                                <div class="btn-group shadow-sm" role="group">

                                                    <a href="MovimientoController?accion=editar&id=${mov.id}&idcategoria=${param.idcategoria}&nombre=${param.nombre}"
                                                       class="btn btn-sm btn-outline-primary btn-accion" title="Editar">
                                                        <i class="bi bi-pencil-square"></i>
                                                    </a>

                                                    <a href="MovimientoController?accion=eliminar&id=${mov.id}&idcategoria=${param.idcategoria}&nombre=${param.nombre}"
                                                       class="btn btn-sm btn-outline-danger btn-accion"
                                                       onclick="return confirm('¿Seguro que deseas eliminar este gasto?');" title="Eliminar">
                                                        <i class="bi bi-trash3-fill"></i>
                                                    </a>

                                                </div>
                                            </td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty movimientos}">
                                    <tr>
                                        <td colspan="4" class="text-center py-5 text-muted">
                                            <i class="bi bi-receipt fs-1 d-block mb-2 text-secondary opacity-50"></i>
                                            No hay gastos registrados en esta categoría aún.
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

<!-- Script de Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>