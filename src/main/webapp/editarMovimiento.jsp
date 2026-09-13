<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Gasto</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-5">

            <!-- Tarjeta principal -->
            <div class="card shadow-sm border-0 rounded-4 overflow-hidden">

                <div class="card-header bg-primary text-white text-center py-4 border-0">
                    <h4 class="mb-0 fw-bold">
                        <i class="bi bi-pencil-square"></i>
                        Editar Gasto
                    </h4>
                    <p class="mb-0 mt-1 small opacity-75">Categoría: <strong>${param.nombre}</strong></p>
                </div>

                <div class="card-body p-4 p-md-5">
                    <form action="MovimientoController?accion=${empty movimiento ? 'crear' : 'actualizar'}" method="POST">


                        <input type="hidden" name="id" value="${movimiento.id}">
                        <!-- El ID y Nombre de la categoría para no perdernos al volver -->
                        <input type="hidden" name="idcategoria" value="${param.idcategoria}">
                        <input type="hidden" name="nombre" value="${param.nombre}">

                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary small">CONCEPTO / DESCRIPCIÓN</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white text-primary border-end-0"><i class="bi bi-card-text"></i></span>
                                <input type="text" class="form-control border-start-0 py-2 px-3" name="concepto"
                                       value="${movimiento.concepto}" placeholder="Ej. Cena con amigos" required autofocus>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary small">MONTO</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white text-danger fw-bold border-end-0">S/</span>
                                <input type="number" step="0.01" class="form-control border-start-0 py-2 px-3" name="monto"
                                       value="${movimiento.monto}" placeholder="0.00" required>
                            </div>
                        </div>

                        <div class="mb-5">
                            <label class="form-label fw-bold text-secondary small">FECHA</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white text-primary border-end-0"><i class="bi bi-calendar-date"></i></span>
                                <input type="date" class="form-control border-start-0 py-2 px-3" name="fecha"
                                       value="${movimiento.fecha}" required>
                            </div>
                        </div>

                        <!-- Botones de Acción -->
                        <div class="d-flex flex-column gap-3">
                            <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm w-100">
                                <i class="bi ${empty movimiento ? 'bi-check-circle-fill' : 'bi-save-fill'} me-2"></i>
                                ${empty movimiento ? 'Guardar Gasto' : 'Actualizar Cambios'}
                            </button>

                            <!-- Botón de regreso que usa los parámetros para volver a la categoría correcta -->
                            <a href="MovimientoController?accion=listar&idcategoria=${param.idcategoria}&nombre=${param.nombre}"
                               class="btn btn-light border text-secondary fw-bold w-100 py-2">
                                <i class="bi bi-arrow-left-circle me-2"></i>Cancelar y Volver
                            </a>
                        </div>

                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>