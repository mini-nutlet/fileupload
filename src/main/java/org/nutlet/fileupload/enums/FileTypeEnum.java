package org.nutlet.fileupload.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * File type and corresponding file magic number enumeration class
 */

public enum FileTypeEnum {

    /**
     * JPEG (jpg)
     */
    JPEG("JPG", "FFD8FF"),

    /**
     * PNG
     */
    PNG("PNG", "89504E47"),

    /**
     * GIFs
     */

    /**
     * TIFF (tif)
     */

    TIFF("TIF", "49492A00"),

    /**
     * Windows bitmap (bmp)
     */

    BMP("BMP", "424D"),

    BMP_16("BMP", "424D228C010000000000"), //16-color bitmap (bmp)

    BMP_24("BMP", "424D82400900000000000"), //24-bit bitmap (bmp)

    BMP_256("BMP", "424D8E1B030000000000"), //256-color bitmap (bmp)

    /**
     * CAD (dwg)
     */

    DWG("DWG", "41433130"),

    /**
     * Adobe photoshop (psd)
     */

    PSD("PSD", "38425053"),

    /**
     * Rich Text Format (rtf)
     */

    RTF("RTF", "7B5C727466"),

    /**
     * XML
     */

    XML("XML", "3C3F786D6C"),

    /**
     * HTML (html)
     */

    HTML("HTML", "68746D6C3E"),

    /**
     * Email [thorough only] (eml)
     */

    EML("EML", "44656C69766572792D646174653A"),

    /**
     * Outlook Express (dbx)
     */

    DBX("DBX", "CFAD12FEC5FD746F "),

    /**
     * Outlook (pst)
     */

    PST("", "2142444E"),

    /**
     * doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db
     */

    OLE2("OLE2", "0xD0CF11E0A1B11AE1"),

    /**
     * Microsoft Word/Excel Note: The file headers of word and excel are the same
     */

    XLS("XLS", "D0CF11E0"),

    /**
     * Microsoft Word/Excel Note: The file headers of word and excel are the same
     */

    DOC("DOC", "D0CF11E0"),

    /**
     * Microsoft Word/Excel 2007 and above files Note: The file headers of word and excel are the same
     */

    DOCX("DOCX", "504B0304"),

    /**
     * Microsoft Word/Excel 2007 and above files Note: The file headers of word and excel are the same 504B030414000600080000002100
     */

    XLSX("XLSX", "504B0304"),

    /**
     * Microsoft Access (mdb)
     */

    MDB("MDB", "5374616E64617264204A"),

    /**
     * Word Perfect (wpd)
     */

    WPB("WPB", "FF575043"),

    /**
     * Postscript
     */

    EPS("EPS", "252150532D41646F6265"),

    /**
     * Postscript
     */

    PS("PS", "252150532D41646F6265"),

    /**
     * Adobe Acrobat (pdf)
     */

    PDF("PDF", "255044462D312E"),

    /**
     * Quicken (qdf)
     */

    QDF("qdf", "AC9EBD8F"),

    /**
     * QuickBooks Backup (qdb)
     */

    QDB("qbb", "458600000600"),

    /**
     * Windows Password (pwl)
     */

    PWL("PWL", "E3828596"),

    /**
     * ZIP Archive
     */

    ZIP("", "504B0304"),

    /**
     * ARAR Archive
     */

    RAR("", "52617221"),

    /**
     * WAVE (wav)
     */

    WAV("WAV", "57415645"),

    /**
     * AVI
     */

    AVI("AVI", "41564920"),

    /**
     * Real Audio (ram)
     */

    RAM("RAM", "2E7261FD"),

    /**
     * Real Media (rm) rmvb/rm is the same
     */

    RM("RM", "2E524D46"),

    /**
     * Real Media (rm) rmvb/rm is the same
     */

    RMVB("RMVB", "2E524D46000000120001"),

    /**
     * MPEG (mpg)
     */

    MPG("MPG", "000001BA"),

    /**
     * Quicktime (mov)
     */

    MOV("MOV", "6D6F6F76"),

    /**
     * Windows Media (asf)
     */

    ASF("ASF", "3026B2758E66CF11"),

    /**
     * ARJ Archive
     */

    ARJ("ARJ", "60EA"),

    /**
     * MIDI (mid)
     */

    MID("MID", "4D546864"),

    /**
     * MP4
     */

    MP4("MP4", "00000020667479706D70"),

    /**
     * MP3
     */

    MP3("MP3", "49443303000000002176"),

    /**
     * FLV
     */

    FLV("FLV", "464C5601050000000900"),

    /**
     * 1F8B0800000000000000
     */

    GZ("GZ", "1F8B08"),

    /**
     * CSS
     */

    CSS("CSS", "48544D4C207B0D0A0942"),

    /**
     * JS
     */

    JS("JS", "696B2E71623D696B2E71"),

    /**
     * Visio
     */

    VSD("VSD", "d0cf11e0a1b11ae10000"),

    /**
     * WPS text wps, form et, demo dps are all the same
     */

    WPS("WPS", "d0cf11e0a1b11ae10000"),

    /**
     * torrent
     */

    TORRENT("TORRENT", "6431303A637265617465"),

    /**
     * JSP Archive
     */

    JSP("JSP", "3C2540207061676520"),

    /**
     * JAVA Archive
     */

    JAVA("JAVA", "7061636B61676520"),

    /**
     * CLASS Archive
     */

    CLASS("CLASS", "CAFEBABE0000002E00"),

    /**
     * JAR Archive
     */
    JAR("JAR", "504B03040A000000"),

    /**
     * MF Archive
     */
    MF("MF", "4D616E69666573742D56"),

    /**
     * EXE Archive
     */
    EXE("EXE", "4D5A9000030000000400"),

    /**
     * ELF Executable
     */
    ELF("ELF", "7F454C4601010100"),

    /**
     * Lotus 123 v1
     */
    WK1("WK1", "2000604060"),

    /**
     * Lotus 123 v3
     */
    WK3("WK3", "00001A0000100400"),

    /**
     * Lotus 123 v5
     */
    WK4("WK4", "00001A0002100400"),

    /**
     * Lotus WordPro v9
     */
    LWP("LWP", "576F726450726F"),

    /**
     * Sage(sly.or.srt.or.slt;sly;srt;slt)
     */
    SLY("SLY", "53520100"),

    /**
     * CHM Archive
     */
    CHM("CHM", "49545346030000006000"),
    INI("INI", "235468697320636F6E66"),
    SQL("SQL", "494E5345525420494E54"),
    BAT("BAT", "406563686F206f66660D"),
    PROPERTIES("", "6C6F67346A2E726F6F74"),
    MXP("", "04000000010000001300"),
    NOT_EXITS_ENUM("", "");

    /**
     * The name corresponding to the file type
     */
    private String fileTypeName;

    /**
     * The magic number corresponding to the file type
     */
    private String magicNumberCode;

    private FileTypeEnum(String fileTypeName, String magicNumberCode) {
        this.fileTypeName = fileTypeName;
        this.magicNumberCode = magicNumberCode;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public String getMagicNumberCode() {
        return magicNumberCode;
    }

    /**
     * Obtain the file type magic number encoding according to the file type
     * Returns standard parts by default
     *
     * @param magicNumberCode - file type magic number code
     * @return
     */
    public static FileTypeEnum getByMagicNumberCode(String magicNumberCode) {
        if (StringUtils.isNotBlank(magicNumberCode)) {
            for (FileTypeEnum type : values()) {
                if (magicNumberCode.toUpperCase().startsWith(type.getMagicNumberCode())) {
                    return type;
                }
            }
        }
        return FileTypeEnum.NOT_EXITS_ENUM;
    }

    /**
     * Get the enumeration according to the file type suffix
     *
     * @param fileTypeName - file type suffix name
     * @return
     */

    public static FileTypeEnum getByFileTypeName(String fileTypeName) {

        if (StringUtils.isNotBlank(fileTypeName)) {
            for (FileTypeEnum type : values()) {
                if (type.getFileTypeName().equals(fileTypeName)) {
                    return type;
                }
            }
        }
        return FileTypeEnum.NOT_EXITS_ENUM;
    }

}