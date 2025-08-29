package app.bhavarlal.trilokchandhi.sons.ltd.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseReport;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderPdfResponse;

public class DeliveryVoucherPdfGenerator {

    public static void generateExpenseReportPdf(Context context, List<ExpenseReport.Datum> report)
            throws IOException {
        try {
            String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
            File dir = new File(path);

            File file = new File(path, System.currentTimeMillis() + "_expense.pdf");

            if (!dir.exists()) {
                dir.mkdirs();
            }


            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);
            // Fonts
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont normal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Header
            document.add(new Paragraph("Bhavarlal Trilokchandji and Sons (P) Ltd.").setFont(font).setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Expense Report").setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));

            // Salesman Info
           /* document.add(new Paragraph("Name of Salesman: " + report.get(0).getUser().getFirstName() +
                    " "+ report.get(0).getUser().getLastName()).setFont(normal));
            document.add(new Paragraph("Date: " + report.get(0).getDateDmy()).setFont(normal));
            document.add(new Paragraph("From: " + report.get(0).getDeliveryVoucher().getFromLocation()).setFont(normal));
            document.add(new Paragraph("To: " + report.get(0).getDeliveryVoucher().getToLocation()).setFont(normal));
            document.add(new Paragraph("Advance: ₹" + report.get(0).getDeliveryVoucher().getDeliveryProducts().getCash()).setFont(normal));
            document.add(new Paragraph("\n"));
            */
         /*   Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
            infoTable.setWidth(UnitValue.createPercentValue(100));

            infoTable.addCell(new Cell().add(new Paragraph("Name of Salesman: " + report.get(0).getUser().getFirstName() +
                    " "+ report.get(0).getUser().getLastName()).setFont(normal)));

            infoTable.addCell(new Cell().add(new Paragraph("Date: " + report.get(0).getDateDmy()).setFont(normal))
                    .setTextAlignment(TextAlignment.RIGHT));

            infoTable.addCell(new Cell().add(new Paragraph("From: " + report.get(0).getDeliveryVoucher().getFromLocation()).setFont(normal)));
            infoTable.addCell(new Cell().add(new Paragraph("Advance: ₹" + report.get(0).getDeliveryVoucher().getDeliveryProducts().getCash()).setFont(normal)).setTextAlignment(TextAlignment.RIGHT));

            infoTable.addCell(new Cell(1, 2).add(new Paragraph("To: " + report.get(0).getDeliveryVoucher().getToLocation())
                    .setFont(normal)));

            document.add(infoTable);*/
            // Left Table (Name, From, To)
            Table leftTable = new Table(1)
                    .setWidth(UnitValue.createPercentValue(70));

            leftTable.addCell(new Cell().add(new Paragraph("Name of Salesman: " + report.get(0).getUser().getFirstName() +
                    " " + report.get(0).getUser().getLastName()).setFont(normal)));
            leftTable.addCell(new Cell().add(new Paragraph("From: " + report.get(0).getDeliveryVoucher().getFromLocation()).setFont(normal)));
            leftTable.addCell(new Cell().add(new Paragraph("To: " + report.get(0).getDeliveryVoucher().getToLocation()).setFont(normal)));

// Right Table (Date, Advance)
            Table rightTable = new Table(1)
                    .setWidth(UnitValue.createPercentValue(50));

            rightTable.addCell(new Cell().add(new Paragraph("Date: " + report.get(0).getDateDmy()).setFont(normal)));
            rightTable.addCell(new Cell().add(new Paragraph("Advance: ₹" + report.get(0).getDeliveryVoucher().getDeliveryProducts().getCash()).setFont(normal)));

// Container table (1 row, 2 columns)
            Table container = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .setWidth(UnitValue.createPercentValue(100));

            container.addCell(new Cell().add(leftTable).setBorder(Border.NO_BORDER));
            container.addCell(new Cell().add(rightTable).setBorder(Border.NO_BORDER));

            document.add(container);


            document.add(new Paragraph("\n"));

            // Table Headers
            Table table = new Table(new float[]{2, 3, 2, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));
            String[] headers = {"Date", "Name & Place", "Lodging", "Travelling", "Food", "Others"};
            for (String h : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(h).setFont(font)));
            }

            // Table Rows
            for (ExpenseReport.Datum item : report) {
                table.addCell(item.getDateDmy());
                table.addCell(item.getNamePlace());
                table.addCell(String.valueOf(item.getLodging()));
                table.addCell(String.valueOf(item.getTravelling()));
                table.addCell(String.valueOf(item.getFood()));
                table.addCell(String.valueOf(item.getOther()));
            }

            document.add(table);
            document.close();

            Toast.makeText(context, "Expense Report created", Toast.LENGTH_LONG).show();

            openPdfFile(context, file);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void generatePdf(Context context, OrderPdfResponse.Data data)
            throws IOException {
        try {
            String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
            File file = new File(path, System.currentTimeMillis() + "_estimate.pdf");

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
//            document.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.setMargins(20, 0, 20, 0);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            Paragraph subTitle = new Paragraph("Estimate/On Approval\nBhavarlal Trilokchandji and Sons (P) Ltd.")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold();
            document.add(subTitle);

            Table headerTable = new Table(new float[]{1, 1});
            headerTable.setWidth(UnitValue.createPercentValue(100));
            headerTable.addCell(getCell("Party's Name :- " + data.getCustomer().getName() +
                    "\nAddress :- " + data.getCustomer().getAddress() + "\nContact :- " +
                    data.getCustomer().getContact(), font));
            headerTable.addCell(getCell("No.: " + (1000+data.getId()) + "\nDate: " + data.getOrderDateDmy(), font));
            document.add(headerTable);

            // Add horizontal line before header
            LineSeparator lineBefore = new LineSeparator(new ILineDrawer() {
                @Override
                public void draw(PdfCanvas canvas, Rectangle drawArea) {
                    canvas.saveState()
                            .setLineWidth(0.5f)
                            .moveTo(drawArea.getX(), drawArea.getY() + drawArea.getHeight() / 2)
                            .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + drawArea.getHeight() / 2)
                            .stroke()
                            .restoreState();
                }

                @Override
                public float getLineWidth() {
                    return 0.5f;
                }

                @Override
                public void setLineWidth(float lineWidth) {

                }

                @Override
                public Color getColor() {
                    return null;
                }

                @Override
                public void setColor(Color color) {

                }
            });
            document.add(lineBefore);

            // Items Table
            Table itemTable = new Table(new float[]{2, 1, 1, 1, 1, 1, 1, 1});
            itemTable.setWidth(UnitValue.createPercentValue(100));

            Cell itemLabel = new Cell().add(new Paragraph("Items"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(itemLabel);

            Cell qtyLabel = new Cell().add(new Paragraph("Qty"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(qtyLabel);

            Cell grossLabel = new Cell().add(new Paragraph("GW"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(grossLabel);

            Cell lessLabel = new Cell().add(new Paragraph("LW"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(lessLabel);

            Cell purityLabel = new Cell().add(new Paragraph("P."))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(purityLabel);

            Cell wastageLabel = new Cell().add(new Paragraph("W."))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(wastageLabel);

            Cell fineLabelTitle = new Cell().add(new Paragraph("Fine"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(fineLabelTitle);

            Cell lbrLabel = new Cell().add(new Paragraph("Lbr/Pc"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addHeaderCell(lbrLabel);


//                itemTable.addHeaderCell("Items").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("Qty").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("GW").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("LW").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("P.").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("W.").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("Fine").setTextAlignment(TextAlignment.CENTER);
//                itemTable.addHeaderCell("Lbr/Pc").setTextAlignment(TextAlignment.CENTER);


            itemTable.addCell(new Cell(1, 8)
                    .add(new LineSeparator(new ILineDrawer() {
                        @Override
                        public void draw(PdfCanvas canvas, Rectangle drawArea) {
                            canvas.saveState()
                                    .setLineWidth(0.5f)
                                    .moveTo(drawArea.getX(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .stroke()
                                    .restoreState();
                        }

                        @Override
                        public float getLineWidth() {
                            return 0.5f;
                        }

                        @Override
                        public void setLineWidth(float lineWidth) {

                        }

                        @Override
                        public Color getColor() {
                            return null;
                        }

                        @Override
                        public void setColor(Color color) {

                        }
                    }))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(5));


            double totalQty = 0, totalGW = 0, totalLW = 0, totalFine = 0, totalLabour = 0, totalPurity = 0,
                    totalWastage = 0, totalLabourAmt = 0;

            for (OrderPdfResponse.OrderProduct item : data.getOrderProduct()) {

                if (!item.getProductDetails().getProductName().equals("")) {
                    Cell nameValue = new Cell().add(new Paragraph(item.getProductDetails().getProductName()))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(nameValue);
                } else {
                    Cell nameValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(nameValue);
                }


                if (Double.parseDouble(item.getQuantity()) != 0.0) {
                    Cell qtyValue = new Cell().add(new Paragraph(String.valueOf(item.getQuantity())))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(qtyValue);
                } else {
                    Cell qtyValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(qtyValue);
                }


                if (Double.parseDouble(item.getGrossWeight()) != 0.0) {
                    Cell gwValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getGrossWeight()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(gwValue);
                } else {
                    Cell gwValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(gwValue);
                }


                if (Double.parseDouble(item.getLessWeight()) != 0.0) {
                    Cell lessValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getLessWeight()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(lessValue);
                } else {
                    Cell lessValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(lessValue);
                }

                if (Double.parseDouble(item.getPurity()) != 0.0) {
                    Cell purityValue1 = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getPurity()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                itemTable.addCell(purityValue1);
                } else {
                    Cell purityValue1 = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                itemTable.addCell(purityValue1);
                }
               /* Cell lessValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getLessWeight()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                itemTable.addCell(lessValue);*/

                /*Cell purityValue1 = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getPurity()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                itemTable.addCell(purityValue1);*/

                if (Double.parseDouble(item.getWastage()) != 0.0) {
                    Cell wastgValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getWastage()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(wastgValue);
                } else {
                    Cell wastgValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(wastgValue);
                }

                /*Cell wastgValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getWastage()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                itemTable.addCell(wastgValue);*/

                if (Double.parseDouble(item.getFine()) != 0.0) {
                    Cell fineValue1 = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(item.getFine()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(fineValue1);
                } else {
                    Cell fineValue1 = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(fineValue1);
                }

                if (Double.parseDouble(item.getLaboureAmount()) != 0.0) {
                    Cell lbrValue1 = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(item.getLaboureAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(lbrValue1);
                } else {
                    Cell lbrValue1 = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    itemTable.addCell(lbrValue1);
                }

//                    itemTable.addCell(item.getProductDetails().getProductName());
//                    itemTable.addCell(String.valueOf(item.getQuantity()));
//                    itemTable.addCell(String.valueOf(String.format("%.3f",Double.parseDouble(item.getGrossWeight()))));
//                    itemTable.addCell(String.valueOf(String.format("%.3f",Double.parseDouble(item.getLessWeight()))));
//                    itemTable.addCell(String.valueOf(String.format("%.3f",Double.parseDouble(item.getPurity()))));
//                    itemTable.addCell(String.valueOf(String.format("%.3f",Double.parseDouble(item.getWastage()))));
//                    itemTable.addCell(String.format("%.3f", Double.parseDouble(item.getFine())));
//                    itemTable.addCell(String.valueOf(Math.round(Double.parseDouble(item.getLaboureAmount()))));
                totalQty += Double.parseDouble(item.getQuantity());
                totalGW += Double.parseDouble(item.getGrossWeight());
                totalLW += Double.parseDouble(item.getLessWeight());
                totalFine += Double.parseDouble(item.getFine());
                totalLabour += Double.parseDouble(item.getLaboureAmount());
                totalPurity += Double.parseDouble(item.getPurity());
                totalWastage += Double.parseDouble(item.getWastage());
                totalLabourAmt += Double.parseDouble(item.getLaboureRate());

            }
// Add blank spacer row before totals
            for (int i = 0; i < 8; i++) {
                itemTable.addCell(new Cell().add(new Paragraph(" "))
                        .setBorder(Border.NO_BORDER)
                        .setHeight(50)); // adjust for spacing if needed
            }
            itemTable.addCell(new Cell(1, 8)
                    .add(new LineSeparator(new ILineDrawer() {
                        @Override
                        public void draw(PdfCanvas canvas, Rectangle drawArea) {
                            canvas.saveState()
                                    .setLineWidth(0.5f)
                                    .moveTo(drawArea.getX(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .stroke()
                                    .restoreState();
                        }

                        @Override
                        public float getLineWidth() {
                            return 0.5f;
                        }

                        @Override
                        public void setLineWidth(float lineWidth) {

                        }

                        @Override
                        public Color getColor() {
                            return null;
                        }

                        @Override
                        public void setColor(Color color) {

                        }
                    }))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(5));

            Cell titleValue1 = new Cell().add(new Paragraph("Total"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(titleValue1);

            Cell totalQtyValue1 = new Cell().add(new Paragraph(String.valueOf(Math.round(totalQty))))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalQtyValue1);

            Cell totalGwValue1 = new Cell().add(new Paragraph(String.format("%.3f", totalGW)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalGwValue1);

            Cell totalLWValue1 = new Cell().add(new Paragraph(String.format("%.3f", totalLW)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalLWValue1);

            Cell totalPurityValue1 = new Cell().add(new Paragraph(String.format("%.3f", totalPurity)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalPurityValue1);

            Cell totalWstgValue1 = new Cell().add(new Paragraph(String.format("%.3f", totalWastage)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalWstgValue1);

            Cell totalfnValue1 = new Cell().add(new Paragraph(String.format("%.3f", totalFine)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totalfnValue1);

            Cell totallbrValue1 = new Cell().add(new Paragraph(String.valueOf(Math.round(totalLabour))))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            itemTable.addCell(totallbrValue1);
            itemTable.addCell(new Cell(1, 8)
                    .add(new LineSeparator(new ILineDrawer() {
                        @Override
                        public void draw(PdfCanvas canvas, Rectangle drawArea) {
                            canvas.saveState()
                                    .setLineWidth(0.5f)
                                    .moveTo(drawArea.getX(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + drawArea.getHeight() / 2)
                                    .stroke()
                                    .restoreState();
                        }

                        @Override
                        public float getLineWidth() {
                            return 0.5f;
                        }

                        @Override
                        public void setLineWidth(float lineWidth) {

                        }

                        @Override
                        public Color getColor() {
                            return null;
                        }

                        @Override
                        public void setColor(Color color) {

                        }
                    }))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingBottom(5));
            // Totals
//                itemTable.addCell(new Cell().add(new Paragraph("Total")).setBold()).setTextAlignment(TextAlignment.CENTER);
//                itemTable.addCell(String.valueOf(Math.round(totalQty))).setTextAlignment(TextAlignment.CENTER);
//                itemTable.addCell(String.format("%.3f", totalGW)).setTextAlignment(TextAlignment.CENTER);
//                itemTable.addCell(String.format("%.3f", totalLW)).setTextAlignment(TextAlignment.CENTER);
//                itemTable.addCell(String.format("%.3f", totalPurity)).setTextAlignment(TextAlignment.CENTER); // P.
//                itemTable.addCell(String.format("%.3f", totalWastage)).setTextAlignment(TextAlignment.CENTER); // W.
//                itemTable.addCell(String.format("%.3f", totalFine)).setTextAlignment(TextAlignment.CENTER);
//                itemTable.addCell(String.valueOf(Math.round(totalLabour))).setTextAlignment(TextAlignment.CENTER); // Lbr/Pc

            document.add(itemTable);
            document.add(new Paragraph("\n"));

            Table totals = new Table(2);
            totals.setWidth(UnitValue.createPercentValue(100));
            Cell fineLabel = new Cell().add(new Paragraph("Fine"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            totals.addCell(fineLabel);

            Double fineMain = ((totalFine / Double.parseDouble(data.getOtherPurity())) * 100);
            if (!String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity())) * 100)).equals("0.000")) {
                Cell fineValue = new Cell().add(new Paragraph(String.valueOf(String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity())) * 100)))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(fineValue);

            } else {
                Cell fineValue = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(fineValue);

            }

            Cell oldFineLabel = new Cell().add(new Paragraph("Old Fine"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            totals.addCell(oldFineLabel);
            if (!String.format("%.3f", Double.parseDouble(data.getOldFine())).equals("0.000")) {
                Cell oldFineValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getOldFine()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(oldFineValue);
            } else {
                Cell oldFineValue = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(oldFineValue);
            }


            Cell totalFineLabel = new Cell().add(new Paragraph("Total Fine"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            totals.addCell(totalFineLabel);

            if (!String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity())) * 100) + Double.parseDouble(data.getOldFine())).equals("0.000")) {
                Cell totalFineValue = new Cell().add(new Paragraph(String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity())) * 100) + Double.parseDouble(data.getOldFine()))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(totalFineValue);
            } else {
                Cell totalFineValue = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(totalFineValue);
            }

//                totals.addCell("Total Fine").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                totals.addCell(String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity()))*100) + Double.parseDouble(data.getOldFine()))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);

          /*  Cell labourLabel = new Cell().add(new Paragraph("Labour"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            totals.addCell(labourLabel);

            if (totalLabour != 0.0) {
                Cell labourValue = new Cell().add(new Paragraph(String.valueOf(Math.round(totalLabour))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(labourValue);
            } else {
                Cell labourValue = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(labourValue);
            }


//                totals.addCell("Labour").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                totals.addCell(String.valueOf(Math.round(totalLabour))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);

            Cell oldAmtLabel = new Cell().add(new Paragraph("Old Amount"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER);
            totals.addCell(oldAmtLabel);

            if (Double.parseDouble(data.getOldAmount()) != 0.0) {
                Cell oldAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getOldAmount())))))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(oldAmtValue);
            } else {
                Cell oldAmtValue = new Cell().add(new Paragraph(""))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(oldAmtValue);
            }*/

//                totals.addCell("Old Amount").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                totals.addCell(String.valueOf(Math.round(Double.parseDouble(data.getOldAmount())))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                document.add(totals);
            Table price = new Table(2);
            price.setWidth(UnitValue.createPercentValue(100));

            Table tableCheque = new Table(4);
            tableCheque.setWidth(UnitValue.createPercentValue(50));

            Table tableRcvd = new Table(2);
            tableRcvd.setWidth(UnitValue.createPercentValue(100));

            if (data.getModeOfPayment().equals("GST")) {
//                    document.add(new Paragraph("\n"));
                // Price Calculation


//                double amount = data.getBalanceFine() * Double.parseDouble(data.getGoldRate());
                double amount = Double.parseDouble(data.getAmount());
//                    double amount = Double.parseDouble(String.format("%.3f", ((totalFine / Double.parseDouble(data.getOtherPurity()))*100))) * Double.parseDouble(data.getGoldRate());

                double gst = (amount * (Double.parseDouble(data.getGstPercentage()) / 100)) +
                        (totalLabour * (Double.parseDouble(data.getGstPercentage()) / 100));
                double totalAmount = amount + gst + totalLabour + Double.parseDouble(data.getOldAmount());

                Cell labourLabel = new Cell().add(new Paragraph("Labour"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(labourLabel);

                if (totalLabour != 0.0) {
                    Cell labourValue = new Cell().add(new Paragraph(String.valueOf(Math.round(totalLabour))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(labourValue);
                } else {
                    Cell labourValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(labourValue);
                }


//                totals.addCell("Labour").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                totals.addCell(String.valueOf(Math.round(totalLabour))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);

                Cell oldAmtLabel = new Cell().add(new Paragraph("Old Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(oldAmtLabel);

                if (Double.parseDouble(data.getOldAmount()) != 0.0) {
                    Cell oldAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getOldAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(oldAmtValue);
                } else {
                    Cell oldAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(oldAmtValue);
                }

                Cell gstLabel = new Cell().add(new Paragraph("Gold Rate(GST)"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(gstLabel);

                if (Double.parseDouble(data.getGoldRate()) != 0.0) {
                    Cell gstValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getGoldRate())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(gstValue);
                }else{
                    Cell gstValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(gstValue);
                }

//                    price.addCell("Gold Rate(GST)").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.valueOf(data.getGoldRate())).setTextAlignment(TextAlignment.CENTER);

                Cell amtLabel = new Cell().add(new Paragraph("Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(amtLabel);

                if (amount != 0.0) {

                    Cell amtValue = new Cell().add(new Paragraph(String.format("%.0f", amount)))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(amtValue);
                } else {
                    Cell amtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(amtValue);
                }

//                    price.addCell("Amount").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.format("%.0f", amount)).setTextAlignment(TextAlignment.CENTER);

                Cell gstPerLabel = new Cell().add(new Paragraph("GST " + data.getGstPercentage() + "%"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(gstPerLabel);

                if (gst != 0.0) {
                    Cell gstPerValue = new Cell().add(new Paragraph(String.format("%.0f", gst)))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(gstPerValue);
                } else {
                    Cell gstPerValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(gstPerValue);
                }


//                    price.addCell("GST " + data.getGstPercentage() + "%").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.format("%.0f", gst)).setTextAlignment(TextAlignment.CENTER);

                Cell totalAmtLabel = new Cell().add(new Paragraph("Total Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(totalAmtLabel);

                if (totalAmount != 0.0) {
                    Cell totalAmtValue = new Cell().add(new Paragraph(String.format("%.0f", totalAmount)))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(totalAmtValue);
                } else {
                    Cell totalAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(totalAmtValue);
                }
//                    price.addCell("Total Amount").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.format("%.0f", totalAmount)).setTextAlignment(TextAlignment.CENTER);


                Cell rcvdAmtLabel = new Cell().add(new Paragraph("Received Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(rcvdAmtLabel);

                if (Double.parseDouble(data.getReceivedAmount()) != 0.0) {
                    Cell rcvdAmtValue = new Cell().add(new Paragraph(String.format("%.0f", Double.parseDouble(data.getReceivedAmount()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(rcvdAmtValue);
                } else {
                    Cell rcvdAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(rcvdAmtValue);
                }


//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRWeight()))))).setTextAlignment(TextAlignment.CENTER);
//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRPurity()))))).setTextAlignment(TextAlignment.CENTER);
//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRFine()))))).setTextAlignment(TextAlignment.CENTER);
//                    document.add(price);
//                    price.addCell("Received Amount").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.format("%.0f", Double.parseDouble(data.getReceivedAmount()))).setTextAlignment(TextAlignment.CENTER);

                Cell balanceAmtLabel = new Cell().add(new Paragraph("Balance Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(balanceAmtLabel);

                if ((totalAmount - Double.parseDouble(data.getReceivedAmount())) != 0.0) {
                    Cell balanceAmtValue = new Cell().add(new Paragraph(String.format("%.0f", totalAmount - Double.parseDouble(data.getReceivedAmount()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(balanceAmtValue);
                } else {
                    Cell balanceAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(balanceAmtValue);
                }
//                    price.addCell("Balance Amount").setTextAlignment(TextAlignment.CENTER);
//                    price.addCell(String.format("%.0f", totalAmount - Double.parseDouble(data.getReceivedAmount()))).setTextAlignment(TextAlignment.CENTER);
//                    document.add(price);


                if (data.getPaymentType().equalsIgnoreCase("cheque")) {
//                        Table tableCheque = new Table(4);
//                        tableCheque.setWidth(UnitValue.createPercentValue(50));
                    Cell metalCell = new Cell(2, 1).add(new Paragraph("Cheque"))
                            .setTextAlignment(TextAlignment.CENTER)         // horizontal centering
                            .setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER); // vertical centering
                    tableCheque.addCell(metalCell);

                    Cell weightLabel = new Cell().add(new Paragraph("Chq. No."))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(weightLabel);

                    Cell purityLabelTitle = new Cell().add(new Paragraph("Chq. Date"))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(purityLabelTitle);

                    Cell fineNonGstLabel = new Cell().add(new Paragraph("Bank Name"))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(fineNonGstLabel);

                    Cell metalRWeightValue = new Cell().add(new Paragraph(String.valueOf(data.getChequeNo())))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRWeightValue);

                    Cell metalRPurityValue = new Cell().add(new Paragraph(String.valueOf(data.getChequeDate())))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRPurityValue);

                    Cell metalRFineValue = new Cell().add(new Paragraph(String.valueOf(data.getChequeBankName())))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRFineValue);

//                    document.add(tableCheque);
                } else if (data.getPaymentType().equalsIgnoreCase("NEFT")) {

                    Cell metalCell = new Cell(2, 1).add(new Paragraph("NEFT"))
                            .setTextAlignment(TextAlignment.CENTER)         // horizontal centering
                            .setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER); // vertical centering
                    tableCheque.addCell(metalCell);

                    Cell weightLabel = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(weightLabel);

                    Cell purityLabelTitle = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(purityLabelTitle);

                    Cell fineNonGstLabel = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(fineNonGstLabel);

                    Cell metalRWeightValue = new Cell().add(new Paragraph(String.valueOf("")))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRWeightValue);

                    Cell metalRPurityValue = new Cell().add(new Paragraph(String.valueOf("")))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRPurityValue);

                    Cell metalRFineValue = new Cell().add(new Paragraph(String.valueOf("")))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRFineValue);

//                    document.add(tableCheque);
                }


            } else {
//                document.add(new Paragraph("\n"));
                float[] columnWidths = {100f, 100f, 100f, 100f};
//                    Table tableCheque = new Table(4);
//                tableCheque.setWidth(UnitValue.createPercentValue(50));

                // 'Metal Rcvd.' cell with rowspan = 2
//                    Cell metalCell = new Cell(new Phrase("Metal Rcvd.", headerFont));
//                    metalCell.setRowspan(2);
              /*  Cell metalCell = new Cell(2, 1).add(new Paragraph("Metal Rcvd."))
                        .setTextAlignment(TextAlignment.CENTER)         // horizontal centering
                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER); // vertical centering
                tableCheque.addCell(metalCell);

                Cell weightLabel = new Cell().add(new Paragraph("Weight"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                tableCheque.addCell(weightLabel);

                Cell purityLabelTitle = new Cell().add(new Paragraph("Purity"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                tableCheque.addCell(purityLabelTitle);*/

                Cell fineNonGstLabel = new Cell().add(new Paragraph("Fine Rcvd."))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(fineNonGstLabel);

               /* if(Double.parseDouble(data.getMetalRWeight()) != 0.0){
                    Cell metalRWeightValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRWeight()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRWeightValue);
                }else{
                    Cell metalRWeightValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRWeightValue);
                }

                if(Double.parseDouble(data.getMetalRPurity()) != 0.0){
                    Cell metalRPurityValue = new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRPurity()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRPurityValue);
                }else{
                    Cell metalRPurityValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    tableCheque.addCell(metalRPurityValue);
                }*/

                if (Double.parseDouble(data.getMetalRFine()) != 0.0) {

                    Cell metalRFineValue = new Cell().add(new Paragraph(String.format("%.3f",
                            Double.parseDouble(data.getMetalRFine()))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    totals.addCell(metalRFineValue);
                } else {
                    Cell metalRFineValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    totals.addCell(metalRFineValue);
                }
//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRWeight()))))).setTextAlignment(TextAlignment.CENTER);
//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRPurity()))))).setTextAlignment(TextAlignment.CENTER);
//                    table.addCell(new Cell().add(new Paragraph(String.format("%.3f", Double.parseDouble(data.getMetalRFine()))))).setTextAlignment(TextAlignment.CENTER);

//                    document.add(table);

                /*float[] colWidths2 = {200f, 200f};
                Table table2 = new Table(2);
                table2.setWidth(UnitValue.createPercentValue(50));*/

                Cell balanceFineLabel = new Cell().add(new Paragraph("Balance Fine"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                totals.addCell(balanceFineLabel);
//                if (Double.parseDouble(data.getGoldRate()) != 0.0) {
                    if (data.getBalanceFine() != 0.0) {
                        if (Double.parseDouble(data.getGoldRate()) != 0.0){
                            Cell balanceFineValue = new Cell().add(new Paragraph(""))
                                .setTextAlignment(TextAlignment.CENTER)
                                .setBorder(Border.NO_BORDER);
                        totals.addCell(balanceFineValue);
                        }else{
                            Cell balanceFineValue = new Cell().add(new Paragraph(String.format("%.3f", data.getBalanceFine())))
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setBorder(Border.NO_BORDER);
                            totals.addCell(balanceFineValue);
                        }
                    } else {
                        Cell balanceFineValue = new Cell().add(new Paragraph(String.format("%.3f", 0.000)))
                                .setTextAlignment(TextAlignment.CENTER)
                                .setBorder(Border.NO_BORDER);
                        totals.addCell(balanceFineValue);
                    }
                /*}else{
                    Cell balanceFineValue = new Cell().add(new Paragraph(String.format("%.3f", 0.000)))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    totals.addCell(balanceFineValue);
                }*/



                // Remaining rows
//                    table2.addCell(new Cell().add(new Paragraph("Balance Fine"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.format("%.3f", data.getBalanceFine())))).setTextAlignment(TextAlignment.CENTER);

                Cell goldRateLabel = new Cell().add(new Paragraph("Gold Rate"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(goldRateLabel);

                if (Double.parseDouble(data.getGoldRate()) != 0.0) {
                    Cell goldRateValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getGoldRate())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(goldRateValue);
                } else {
                    Cell goldRateValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(goldRateValue);
                }

                Cell labourLabel = new Cell().add(new Paragraph("Labour"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(labourLabel);

                if (totalLabour != 0.0) {
                    Cell labourValue = new Cell().add(new Paragraph(String.valueOf(Math.round(totalLabour))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(labourValue);
                } else {
                    Cell labourValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(labourValue);
                }


//                totals.addCell("Labour").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
//                totals.addCell(String.valueOf(Math.round(totalLabour))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);

                Cell oldAmtLabel = new Cell().add(new Paragraph("Old Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(oldAmtLabel);

                if (Double.parseDouble(data.getOldAmount()) != 0.0) {
                    Cell oldAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getOldAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(oldAmtValue);
                } else {
                    Cell oldAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(oldAmtValue);
                }
//                    table2.addCell(new Cell().add(new Paragraph("Gold Rate"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.valueOf(Math.round(Double.parseDouble(data.getGoldRate())))))).setTextAlignment(TextAlignment.CENTER);

                Cell totalAmtLabel = new Cell().add(new Paragraph("Total Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(totalAmtLabel);

                if (Double.parseDouble(data.getTotalAmount()) != 0.0) {
                    Cell totalAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getTotalAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(totalAmtValue);
                } else {
                    Cell totalAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(totalAmtValue);
                }


//                    table2.addCell(new Cell().add(new Paragraph("Total Amount"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getTotalAmount())))))).setTextAlignment(TextAlignment.CENTER);

                Cell rcvdAmtLabel = new Cell().add(new Paragraph("Received Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(rcvdAmtLabel);

                if (Double.parseDouble(data.getReceivedAmount()) != 0.0) {
                    Cell rcvdAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getReceivedAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(rcvdAmtValue);
                } else {
                    Cell rcvdAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(rcvdAmtValue);
                }


//                    table2.addCell(new Cell().add(new Paragraph("Received Amount"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getReceivedAmount())))))).setTextAlignment(TextAlignment.CENTER);

                Cell roundOffLabel = new Cell().add(new Paragraph("Round off Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(roundOffLabel);

                if (Double.parseDouble(data.getRoundOffAmount()) != 0.0) {
                    Cell roundOffValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getRoundOffAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(roundOffValue);
                } else {
                    Cell roundOffValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(roundOffValue);
                }


//                    table2.addCell(new Cell().add(new Paragraph("Round off Amount"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getRoundOffAmount())))))).setTextAlignment(TextAlignment.CENTER);

                Cell balanceAmtLabel = new Cell().add(new Paragraph("Balance Amount"))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(Border.NO_BORDER);
                price.addCell(balanceAmtLabel);

                if (Double.parseDouble(data.getBalanceAmount()) != 0.0) {
                    Cell balanceAmtValue = new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getBalanceAmount())))))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(balanceAmtValue);
                } else {
                    Cell balanceAmtValue = new Cell().add(new Paragraph(""))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(Border.NO_BORDER);
                    price.addCell(balanceAmtValue);
                }


//                    table2.addCell(new Cell().add(new Paragraph("Balance Amount"))).setTextAlignment(TextAlignment.CENTER);
//                    table2.addCell(new Cell().add(new Paragraph(String.valueOf(Math.round(Float.parseFloat(data.getBalanceAmount())))))).setTextAlignment(TextAlignment.CENTER);

//                document.add(price);
            }


            Table sideBySideTable = new Table(UnitValue.createPercentArray(new float[]{30f, 30f}));
            sideBySideTable.setWidth(UnitValue.createPercentValue(60));
            sideBySideTable.setMarginTop(10);

// Add both tables as cell content
            sideBySideTable.addCell(new Cell().add(totals).setBorder(Border.NO_BORDER));
            sideBySideTable.addCell(new Cell().add(price).setBorder(Border.NO_BORDER));


// Add to document
            document.add(sideBySideTable);
            if (data.getModeOfPayment().equals("GST")) {
                document.add(tableCheque);
            }
            document.add(new Paragraph("\n"));
            // Narration
            Table narration = new Table(2);
            narration.setWidth(UnitValue.createPercentValue(100));
            narration.addCell("Narration");
            narration.addCell("Narration");
            narration.addCell(data.getComment1() == null ||
                    data.getComment1().trim().isEmpty() ? "--" : data.getComment1());
            narration.addCell(data.getComment2() == null ||
                    data.getComment2().trim().isEmpty() ? "--" : data.getComment2());
            document.add(narration);

            document.add(new Paragraph("\n\n\n"));
            document.add(new Paragraph("").setHeight(30)); // 30 points of vertical space

            document.close();
            Toast.makeText(context, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            openPdfFile(context, file);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void openPdfFile(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                file
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            context.startActivity(Intent.createChooser(intent, "Open PDF with"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private static Cell getCell(String text, PdfFont font) {
        return new Cell().add(new Paragraph(text).setFont(font)).setPadding(5).setBorder(Border.NO_BORDER)
                ;
    }

    private static String getGrossWeight(OrderPdfResponse.Data order) {
        return order.getOrderProduct().get(0).getGrossWeight();
    }

    private static void addFooter(Document document) {
        document.add(new Paragraph("OnApproval/Delivery Voucher"));
        document.add(new Paragraph("Gold Ornaments"));
    }

}
