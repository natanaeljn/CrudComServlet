package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class reportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPdf(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {
		JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(listaDados);
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrBean);
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	public byte[] geraRelatorioPdf(List listaDados, String nomeRelatorio, ServletContext servletContext)
			throws Exception {
		JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(listaDados);
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrBean);
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {
		JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(listaDados);
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrBean);

		JRExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();

		return baos.toByteArray();
	}

}
