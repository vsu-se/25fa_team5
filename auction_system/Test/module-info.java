module module_name {
    requires auction_system;
    requires org.junit.jupiter.api;
    requires junit;
    opens Test to org.junit.platform.commons;
}