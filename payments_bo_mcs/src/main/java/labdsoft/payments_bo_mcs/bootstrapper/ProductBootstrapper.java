package labdsoft.payments_bo_mcs.bootstrapper;

import labdsoft.payments_bo_mcs.model.Payments;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//@Profile("bootstrap")
public class ProductBootstrapper implements CommandLineRunner {

    @Autowired
    private PaymentsRepository pRepo;

    @Override
    public void run(String... args) {
        pRepo.deleteAll();

        values ("k485b1l47h5b", "Placa Gráfica Gigabyte GeForce RTX 2060 D6 6G", "placa mais accessivel e decente");
        values ("c475e9l47f5b", "Cooler CPU Nox H-224 ARGB", "arrefecedor com luzinhas");
        values ("n385j1l17h8s", "Cooler CPU Noctua NH-D15 Chromax Black", "arrefecedor sem luzinhas");
        values ("z205bgi47hoc", "Rato Optico Logitech Superlight Wireless 25400DPI", "rato com nome de pessoa");
        values ("h274h5l4563b", "Rato Optico Razer DeathAdder Essential 6400DPI", "rato da marca das cobrinhas");
        values ("b523h5l487kl", "Portátil Lenovo Legion 5 15.6\" 15ACH6", "portatil todo fancy");
        values ("x258f5l48475", "Portátil MSI Raider GE76 12UH-007XPT", "portatil com luzinhas");
        values ("t364gju78354", "Portátil MSI Stealth GS66 12UGS-009PT", "Protatil pra quem pode");
        values ("c6348gn840j1", "Computador Desktop MSI MAG S3 11SH-208XIB", "desktop para o proximo feromonas");
        values ("q73f947gn681", "Teclado Razer Blackwidow V3", "teclado cheio de cores mecanico");
        values ("pa23fvh509a1", "Cartão Microsoft Office 2021 Casa", "autentico roubo");
        values ("d63h57d738s1", "Seat Arona", "La maquina de fiesta");
        values ("asd578fgh267", "Pen", "very good nice product");
        values ("c1d4e7r8d5f2", "Pencil", " writes ");
        values ("c4d4f1v2f5v3", "Rubber", "erases");
        values ("v145dc2365sa", "Wallet", "stores money");
        values ("fg54vc14tr78", "pencil case", " stores pencils");
        values ("12563dcfvg41", "Glasses case", " stores glasses");
        values ("vcg46578vf32", "tissues", " nose clearing material");
        values ("gb576hgb6756", "mouse pad", " mouse adapted surface");
        values ("unbjh875ujg7", " mug ", " drink something from it");
        values ("u1f4f5e2d2xw", " Lamp ", " it lights");
        values ("j85jg76jh845", " Pillow ", " cold both sides");
        values ("g4f7e85f4g54", " chair ", " comfortable ");
    }

    private void values(String sku, String designation, String description) {
        if (pRepo.findBySku(sku).isEmpty()) {
            Payments p1 = new Payments(sku, designation, description);
            pRepo.save(p1);
        }
    }
}
