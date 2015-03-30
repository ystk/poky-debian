# Default library configuration, override if needed
QT_CONFIG_LIBS ?= "-no-libpng"

# FIXME: Other third party librares also should be defined
DEPENDS += "${@base_contains('QT_CONFIG_LIBS', '-system-libpng', 'libpng', '', d)}"
